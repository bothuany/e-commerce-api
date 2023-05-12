package wearwell.com.eCommerceAPI.entities.concretes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "invoices")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "invoiceDate", nullable = false)
    private Date invoiceDate;

    @OneToOne()
    @JoinColumn(name = "orderID", referencedColumnName = "id")
    private Order order;

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", invoiceDate=" + invoiceDate +
                ", order=" + order +
                '}';
    }
}
