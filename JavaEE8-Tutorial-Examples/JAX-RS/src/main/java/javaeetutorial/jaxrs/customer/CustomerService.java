package javaeetutorial.jaxrs.customer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Customer Restful Service with CRUD methods
 */
@Stateless 
@Path("/Customer")
public class CustomerService {

    public static final Logger logger = Logger.getLogger(CustomerService.class.getCanonicalName());
    
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;

    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }

    @GET
    @Path("all")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Customer> getAllCustomers() {
        List<Customer> customers = null;
        try {
            customers = this.findAllCustomers();
            if (customers == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error calling findAllCustomers()", new Object[] { ex.getMessage() });
        }
        return customers;
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Customer getCustomer(@PathParam("id") String customerId) {
        Customer customer = null;
        try {
            customer = findById(customerId);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error calling findCustomer() for customerId {0}. {1}",
                    new Object[] { customerId, ex.getMessage() });
        }
        return customer;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response createCustomer(Customer customer) {
        try {
            long customerId = persist(customer);
            return Response.created(URI.create("/" + customerId)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating customer for customerId {0}. {1}",
                    new Object[] { customer.getId(), e.getMessage() });
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update a resource
     */
    @PUT
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response updateCustomer(@PathParam("id") String customerId, Customer customer) {
        try {
            Customer oldCustomer = findById(customerId);
            if (oldCustomer == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                persist(customer);
                return Response.ok().status(303).build(); // return a seeOther code
            }
        } catch (WebApplicationException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") String customerId) {
        try {
            if (!remove(customerId)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error calling deleteCustomer() for customerId {0}. {1}",
                    new Object[] { customerId, ex.getMessage() });
        }
    }

    /*
     * 
     */
    private long persist(Customer customer) {
        try {
            Address address = customer.getAddress();
            em.persist(address);
            em.persist(customer);
        } catch (Exception ex) {
            logger.warning("Something went wrong when persisting the customer");
        }
        return customer.getId();
    }

    private Customer findById(String customerId) {
        Customer customer = null;
        try {
            customer = em.find(Customer.class, customerId);
            return customer;
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Couldn't fine customer with ID of {0}", customerId);
        }
        return customer;
    }

    private List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = (List<Customer>) em.createNamedQuery("findAllCustomers").getResultList();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error when finding all customers");
        }
        return customers;
    }

    private boolean remove(String customerId) {
        Customer customer;
        try {
            customer = em.find(Customer.class, customerId);
            em.remove(customer.getAddress());
            em.remove(customer);
            return true;
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Couldn't remove customer with ID {0}", customerId);
            return false;
        }
    }
}
