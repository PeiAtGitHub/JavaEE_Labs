package javaeetutorial.customer.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_CUSTOMER")
@NamedQuery(name="findAllCustomers", query="SELECT c FROM Customer c ORDER BY c.id")
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
public class Customer implements Serializable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute(required=true) 
    protected int id;
    
    @XmlElement(required=true) 
    protected String firstname;
    
    @XmlElement(required=true) 
    protected String lastname;
    
    @XmlElement(required=true)
    @OneToOne
    protected Address address;
    
    @XmlElement(required=true)
    protected String email;
 
    @XmlElement (required=true)
    protected String phone;
    
    public Customer() { 
        address = new Address();
    }
    
}
