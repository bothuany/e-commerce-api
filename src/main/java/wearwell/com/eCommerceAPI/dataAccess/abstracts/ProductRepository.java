package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName(String name);

    Optional<Product> findBySlugName(String name);

    Optional<List<Product>> searchAllByCategoryNameAndNameContainingIgnoreCase(String category, String name);
    Optional<List<Product>> findAllBySellerId(String sellerId);

}
