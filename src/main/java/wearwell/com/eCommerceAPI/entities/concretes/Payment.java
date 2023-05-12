package wearwell.com.eCommerceAPI.entities.concretes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "payments")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "amount")
    private double amount;

    @OneToOne
    @JoinColumn(name = "orderID", referencedColumnName = "id")
    private Order order;


}


