package javaeetutorial.producerfields.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ToDo implements Serializable {
    
    private static final long serialVersionUID = 6449030320444473838L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected String taskText;
    
    @Temporal(TIMESTAMP)
    protected Date timeCreated;
    
    public ToDo(String text, Date timeCreated) {
        this.taskText = text;
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "entity.ToDo[id=" + id + "]";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof ToDo)) {
            return false;
        }
        ToDo other = (ToDo) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
