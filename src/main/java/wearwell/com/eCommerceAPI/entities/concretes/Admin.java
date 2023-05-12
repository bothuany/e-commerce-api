package wearwell.com.eCommerceAPI.entities.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wearwell.com.eCommerceAPI.entities.abstracts.User;


@Getter
@Setter
@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    // ...
}
