package javaeetutorial.customer.ejb;

import java.io.Serializable;
import java.util.List;
import javaeetutorial.customer.data.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

@Model
public class CustomerManager implements Serializable {
    @Getter @Setter
    private Customer customer;
    @Setter
    private List<Customer> customers;
    @EJB
    private CustomerBean customerBean;
    
    @PostConstruct
    private void init() {
        customer = new Customer();
        setCustomers(customerBean.retrieveAllCustomers());
    }

    public List<Customer> getCustomers() {
        return customerBean.retrieveAllCustomers();
    }

}
