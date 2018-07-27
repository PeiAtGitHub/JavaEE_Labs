package javaeetutorial.jaxrs.rsvp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@NamedQuery(name="rsvp.entity.Event.getAllUpcomingEvents", query="SELECT e FROM Event e ")
@XmlRootElement(name = "Event")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Getter @Setter
public class Event implements Serializable {
    
    private static final long serialVersionUID = -5584404843358199527L;
    
    @OneToMany(mappedBy = "event")
    private List<Response> responses;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    protected List<Person> invitees;
    protected String name;
    @ManyToOne
    private Person owner;
    protected String location;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date eventDate;

    public Event() {
        this.invitees = new ArrayList<>();
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rsvp.entity.Event[id=" + id + "]";
    }

}
