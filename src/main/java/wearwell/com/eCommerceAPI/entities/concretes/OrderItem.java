package wearwell.com.eCommerceAPI.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "order_items")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stockID", referencedColumnName = "id", nullable = false)
    private Stock stock;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", order=" + order +
                ", stock=" + stock +
                '}';
    }
}
