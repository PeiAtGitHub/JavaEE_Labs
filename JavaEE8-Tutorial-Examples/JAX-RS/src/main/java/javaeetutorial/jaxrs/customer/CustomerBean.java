package javaeetutorial.jaxrs.customer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Named @Stateless
public class CustomerBean {

    private static final Logger logger = Logger.getLogger(CustomerBean.class.getName());

    private static final String CUSTOMER_RETRIEVED = "customerRetrieved";
    private static final String CUSTOMER_CREATED = "customerCreated";
    private static final String CUSTOMER_ERROR = "customerError";
    
    private static final String URL_CUSTOMER = "http://localhost:8080/JAX-RS/webapi/Customer";

    protected Client client;

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void clean() {
        client.close();
    }

    public String createCustomer(Customer customer) {
        if (customer == null) {
            return CUSTOMER_ERROR;
        }
        
        Response response = client.target(URL_CUSTOMER).request(MediaType.APPLICATION_XML)
                .post(Entity.entity(customer, MediaType.APPLICATION_XML), Response.class);
        
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            return CUSTOMER_CREATED;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not create customer."));
            logger.log(Level.WARNING, "couldn''t create customer with id {0}. Status returned was {1}",
                    new Object[] { customer.getId(), response.getStatus() });
            return CUSTOMER_ERROR;
        }
    }

    public String retrieveCustomer(String id) {
        Customer customer = client.target(URL_CUSTOMER).path(id).request(MediaType.APPLICATION_XML).get(Customer.class);
        if (customer == null) {
            return CUSTOMER_ERROR;
        } else {
            return CUSTOMER_RETRIEVED;
        }
    }

    public List<Customer> retrieveAllCustomers() {
        List<Customer> customers = client.target(URL_CUSTOMER).path("all").request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Customer>>() {
                });
        return customers;
    }
}
