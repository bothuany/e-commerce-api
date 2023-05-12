package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByCustomer(Customer customer);
}
