package javaeetutorial.order.web;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.order.ejb.RequestBean;
import javaeetutorial.order.entity.CustomerOrder;
import javaeetutorial.order.entity.LineItem;
import javaeetutorial.order.entity.Part;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;


@ManagedBean
@SessionScoped
public class OrderManager implements Serializable{
    
    private static final long serialVersionUID = 2142383151318489373L;
    private static final Logger logger = Logger.getLogger("order.web.OrderManager");
    
    @EJB
    private RequestBean request;
    private List<CustomerOrder> orders;
    private Integer currentOrder;
    private Integer newOrderId;
    private String newOrderShippingInfo;
    private char newOrderStatus;
    private int newOrderDiscount;
    private List<Part> newOrderParts;
    private List<Part> newOrderSelectedParts;
    private String vendorName;
    private List<String> vendorSearchResults;
    private String selectedPartNumber;
    private int selectedPartRevision;
    private Long selectedVendorPartNumber;
    private Boolean findVendorTableDisabled = false;
    private Boolean partsTableDisabled = true;

    public List<CustomerOrder> getOrders() {
        try {
            this.orders = request.getOrders();
        } catch (Exception e) {
            logger.warning("Couldn't get orders.");
        }
        return orders;
    }

    public List<LineItem> getLineItems() {
        try {
            return request.getLineItems(this.currentOrder);
        } catch (Exception e) {
            logger.warning("Couldn't get lineItems.");
            return null;
        }
    }

    public void removeOrder(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteOrderId");
            request.removeOrder(Integer.parseInt(param.getValue().toString()));
        } catch (NumberFormatException e) {
        }
    }

    public void findVendor() {
        try {
            this.findVendorTableDisabled = true;
            this.vendorSearchResults = (List<String>) request.locateVendorsByPartialName(vendorName);
        } catch (Exception e) {
            logger.warning("Problem calling RequestBean.locateVendorsByPartialName from findVendor");
        }
    }

    public void submitOrder() {
        try {
            request.createOrder(newOrderId, newOrderStatus, newOrderDiscount, newOrderShippingInfo);
            this.newOrderId = null;
            this.newOrderDiscount = 0;
            this.newOrderParts = null;
            this.newOrderShippingInfo = null;
        } catch (Exception e) {
            logger.warning("Problem creating order in submitOrder.");
        }
    }

    public void addLineItem() {
        try {
            List<LineItem> lineItems = request.getLineItems(currentOrder);
            request.addLineItem(this.currentOrder, this.selectedPartNumber, this.selectedPartRevision, 1);
            //this.clearSelected();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Problem adding line items to order ID {0}", newOrderId);
        }
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Integer getNewOrderId() {
        return newOrderId;
    }

    public void setNewOrderId(Integer newOrderId) {
        this.newOrderId = newOrderId;
    }

    public String getNewOrderShippingInfo() {
        return newOrderShippingInfo;
    }
    
    public void setNewOrderShippingInfo(String newOrderShippingInfo) {
        this.newOrderShippingInfo = newOrderShippingInfo;
    }

    public char getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(char newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    public int getNewOrderDiscount() {
        return newOrderDiscount;
    }

    public void setNewOrderDiscount(int newOrderDiscount) {
        this.newOrderDiscount = newOrderDiscount;
    }

    public List<Part> getNewOrderParts() {
        return request.getAllParts();
    }

    public void setNewOrderParts(List<Part> newOrderParts) {
        this.newOrderParts = newOrderParts;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public List<String> getVendorSearchResults() {
        return vendorSearchResults;
    }

    public void setVendorSearchResults(List<String> vendorSearchResults) {
        this.vendorSearchResults = vendorSearchResults;
    }

    public List<Part> getNewOrderSelectedParts() {
        return newOrderSelectedParts;
    }

    public void setNewOrderSelectedParts(List<Part> newOrderSelectedParts) {
        Iterator<Part> i = newOrderSelectedParts.iterator();
        while (i.hasNext()) {
            logger.log(Level.INFO, "Selected part {0}.", i.next().getPartNumber());
        }
        this.newOrderSelectedParts = newOrderSelectedParts;
    }

    public String getSelectedPartNumber() {
        return selectedPartNumber;
    }

    public void setSelectedPartNumber(String selectedPartNumber) {
        this.selectedPartNumber = selectedPartNumber;
    }

    public int getSelectedPartRevision() {
        return selectedPartRevision;
    }

    public void setSelectedPartRevision(int selectedPartRevision) {
        this.selectedPartRevision = selectedPartRevision;
    }

    public Long getSelectedVendorPartNumber() {
        return selectedVendorPartNumber;
    }

    public void setSelectedVendorPartNumber(Long selectedVendorPartNumber) {
        this.selectedVendorPartNumber = selectedVendorPartNumber;
    }

    private void clearSelected() {
        this.setSelectedPartNumber(null);
        this.setSelectedPartRevision(0);
        this.setSelectedVendorPartNumber(new Long(0));
    }

    public Boolean getFindVendorTableDisabled() {
        return findVendorTableDisabled;
    }

    public void setFindVendorTableDisabled(Boolean findVendorTableDisabled) {
        this.findVendorTableDisabled = findVendorTableDisabled;
    }

    public Boolean getPartsTableDisabled() {
        return partsTableDisabled;
    }

    public void setPartsTableDisabled(Boolean partsTableDisabled) {
        this.partsTableDisabled = partsTableDisabled;
    }
}
