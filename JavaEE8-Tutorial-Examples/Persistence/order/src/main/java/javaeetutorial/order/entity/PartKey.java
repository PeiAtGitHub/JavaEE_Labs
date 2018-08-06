package javaeetutorial.order.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PartKey implements Serializable {

    private static final long serialVersionUID = -3162267592969127613L;
    private String partNumber;
    private int revision;

    @Override
    public int hashCode() {
        return ((this.getPartNumber() == null ? 0 : this.getPartNumber().hashCode()) ^ ((int) this.getRevision()));
    }

    @Override
    public boolean equals(Object otherOb) {

        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof PartKey)) {
            return false;
        }
        PartKey other = (PartKey) otherOb;
        return ((this.getPartNumber() == null ? other.getPartNumber() == null
                : this.getPartNumber().equals(other.getPartNumber())) && (getRevision() == other.getRevision()));
    }

    @Override
    public String toString() {
        return getPartNumber() + " rev" + this.getRevision();
    }

}
