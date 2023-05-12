package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
