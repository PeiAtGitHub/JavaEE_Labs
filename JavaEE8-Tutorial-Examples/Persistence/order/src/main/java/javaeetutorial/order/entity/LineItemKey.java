package javaeetutorial.order.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class LineItemKey implements Serializable {

    private static final long serialVersionUID = 1562260205094677677L;

    private Integer customerOrder;
    private int itemId;

    @Override
    public int hashCode() {
        return ((this.getCustomerOrder() == null ? 0 : this.getCustomerOrder().hashCode()) ^ ((int) this.getItemId()));
    }

    @Override
    public boolean equals(Object otherOb) {

        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof LineItemKey)) {
            return false;
        }
        LineItemKey other = (LineItemKey) otherOb;
        return ((this.getCustomerOrder() == null ? other.getCustomerOrder() == null
                : this.getCustomerOrder().equals(other.getCustomerOrder())) && (this.getItemId() == other.getItemId()));
    }

    @Override
    public String toString() {
        return "" + getCustomerOrder() + "-" + getItemId();
    }

}
