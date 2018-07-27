package javaeetutorial.jaxrs.rsvp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Getter @Setter
public class Person implements Serializable {

    private static final long serialVersionUID = -6639818335218185860L;
    @OneToMany(mappedBy = "person")
    @XmlTransient
    private List<Response> responses;
    @OneToMany(mappedBy = "owner")
    @XmlTransient
    private List<Event> ownedEvents;
    @XmlTransient
    @ManyToMany(mappedBy = "invitees")
    private List<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected String firstName;
    protected String lastName;
    
    public Person() {
        this.events = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rsvp.entity.Person[id=" + id + "]";
    }

}
