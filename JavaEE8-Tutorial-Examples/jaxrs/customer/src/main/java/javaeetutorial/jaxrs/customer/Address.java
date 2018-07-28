package javaeetutorial.jaxrs.customer;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_ADDRESS")
@XmlRootElement(name="address")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter @Getter
public class Address implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlElement(required=true) 
    protected int number = 1;

    @XmlElement(required=true)  
    protected String street = "Some Str";
    
    @XmlElement(required=true)  
    protected String city = "Some City";
    
    @XmlElement(required=true) 
    protected String province = "Some Province";
    
    @XmlElement(required=true)  
    protected String zip = "12345";
    
    @XmlElement(required=true)
    protected String country = "Some Country";

}
