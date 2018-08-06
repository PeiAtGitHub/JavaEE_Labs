package javaeetutorial.order.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javaeetutorial.order.entity.CustomerOrder;
import javaeetutorial.order.entity.LineItem;
import javaeetutorial.order.entity.Part;
import javaeetutorial.order.entity.PartKey;
import javaeetutorial.order.entity.Vendor;
import javaeetutorial.order.entity.VendorPart;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class RequestBean {

    @PersistenceContext
    private EntityManager em;

    public void createPart(String partNumber, int revision, String description, java.util.Date revisionDate,
            String specification, Serializable drawing) {
        try {
            em.persist(new Part(partNumber, revision, description, revisionDate, specification, drawing));
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<Part> getAllParts() {
        return (List<Part>) em.createNamedQuery("findAllParts").getResultList();
    }

    public void addPartToBillOfMaterial(String bomPartNumber, int bomRevision, String partNumber, int revision) {
        try {
            Part bom = em.find(Part.class, new PartKey(bomPartNumber, bomRevision));
            Part part = em.find(Part.class, new PartKey(partNumber, revision));
            bom.getParts().add(part);
            part.setBomPart(bom);
        } catch (EJBException e) {
        }
    }

    public void createVendor(int vendorId, String name, String address, String contact, String phone) {
        try {
            em.persist(new Vendor(vendorId, name, address, contact, phone));
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public void createVendorPart(String partNumber, int revision, String description, double price, int vendorId) {
        try {
            Part part = em.find(Part.class, new PartKey(partNumber, revision));

            VendorPart vendorPart = new VendorPart(description, price, part);
            em.persist(vendorPart);
            
            Vendor vendor = em.find(Vendor.class, vendorId);
            vendor.addVendorPart(vendorPart);
            vendorPart.setVendor(vendor);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void createOrder(Integer orderId, char status, int discount, String shipmentInfo) {
        try {
            em.persist(new CustomerOrder(orderId, status, discount, shipmentInfo));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<CustomerOrder> getOrders() {
        try {
            return (List<CustomerOrder>) em.createNamedQuery("findAllOrders").getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addLineItem(Integer orderId, String partNumber, int revision, int quantity) {
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            Part part = em.find(Part.class, new PartKey(partNumber, revision));
            order.addLineItem(new LineItem(order, quantity, part.getVendorPart()));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public double getBillOfMaterialPrice(String bomPartNumber, int bomRevision, String partNumber, int revision) {
        double price = 0.0;
        try {
            Part bom = em.find(Part.class, new PartKey(bomPartNumber, bomRevision));
            for (Part part : bom.getParts()) {
                price += part.getVendorPart().getPrice();
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return price;
    }

    public double getOrderPrice(Integer orderId) {
        double price = 0.0;
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            price = order.calculateAmmount();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return price;
    }

    public void adjustOrderDiscount(int adjustment) {
        try {
            List orders = em.createNamedQuery("findAllOrders").getResultList();
            for(Object order : orders) {
                CustomerOrder o = (CustomerOrder)order;
                int newDiscount = o.getDiscount() + adjustment;
                o.setDiscount((newDiscount > 0) ? newDiscount : 0);
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Double getAvgPrice() {
        try {
            return (Double) em.createNamedQuery("findAverageVendorPartPrice").getSingleResult();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Double getTotalPricePerVendor(int vendorId) {
        try {
            return (Double) em.createNamedQuery("findTotalVendorPartPricePerVendor").setParameter("id", vendorId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<String> locateVendorsByPartialName(String name) {

        List<String> names = new ArrayList<>();
        try {
            List vendors = em.createNamedQuery("findVendorsByPartialName").setParameter("name", name).getResultList();
            for (Iterator iterator = vendors.iterator(); iterator.hasNext();) {
                Vendor vendor = (Vendor) iterator.next();
                names.add(vendor.getName());
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return names;
    }

    public int countAllItems() {
        int count = 0;
        try {
            count = em.createNamedQuery("findAllLineItems").getResultList().size();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return count;
    }

    public List<LineItem> getLineItems(int orderId) {
        try {
            return em.createNamedQuery("findLineItemsByOrderId").setParameter("orderId", orderId).getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeOrder(Integer orderId) {
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            em.remove(order);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public String reportVendorsByOrder(Integer orderId) {
        StringBuilder report = new StringBuilder();
        try {
            List vendors = em.createNamedQuery("findVendorByOrder").setParameter("id", orderId).getResultList();
            for (Iterator iterator = vendors.iterator(); iterator.hasNext();) {
                Vendor vendor = (Vendor) iterator.next();
                report.append(vendor.getVendorId()).append(' ').append(vendor.getName()).append(' ')
                        .append(vendor.getContact()).append('\n');
            }

        } catch (Exception e) {
            throw new EJBException(e);
        }
        return report.toString();
    }
}
