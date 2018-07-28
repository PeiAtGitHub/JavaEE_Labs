package javaeetutorial.jaxrs.customer;

import java.io.Serializable;
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
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute(required=true) 
    protected int id;
    
    @XmlElement(required=true) 
    protected String firstname = "DefaultFN";
    
    @XmlElement(required=true) 
    protected String lastname = "DefaultLN";
    
    @XmlElement(required=true)
    @OneToOne
    protected Address address;
    
    @XmlElement(required=true)
    protected String email = "defaultEm@somewhere.com";
 
    @XmlElement (required=true)
    protected String phone = "1234567890";
    
    public Customer() { 
        address = new Address();
    }
    
}
