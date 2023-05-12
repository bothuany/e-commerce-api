package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Invoice;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    Invoice findByOrder(Order order);
}
