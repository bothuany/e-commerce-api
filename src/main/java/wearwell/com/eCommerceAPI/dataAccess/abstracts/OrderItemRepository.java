package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findAllByOrderId(String orderId);
}
