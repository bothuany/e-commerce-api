package wearwell.com.eCommerceAPI.entities.concretes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "stocks")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "productID", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "colorID", referencedColumnName = "id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "sizeID", referencedColumnName = "id")
    private Size size;


    @Override
    public String toString() {
        return "Stock{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", product=" + product +
                ", color=" + color +
                ", size=" + size +
                '}';
    }
}
