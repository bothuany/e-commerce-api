package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName(String name);
}
