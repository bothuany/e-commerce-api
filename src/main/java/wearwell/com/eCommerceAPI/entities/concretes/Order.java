package wearwell.com.eCommerceAPI.entities.concretes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "orders")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "orderDate", nullable = false)
    private Date orderDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;

    @ManyToOne()
    @JoinColumn(name = "customerID", referencedColumnName = "id")
    private Customer customer;

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", customer=" + customer +
                '}';
    }
}
