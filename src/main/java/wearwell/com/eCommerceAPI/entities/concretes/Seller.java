package wearwell.com.eCommerceAPI.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wearwell.com.eCommerceAPI.entities.abstracts.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("seller")
public class Seller extends User {
    @Column(name = "companyName")
    private String companyName;

    @Column(name = "companyPhoneNumber")
    private String companyPhoneNumber;
}
