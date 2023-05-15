package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<List<Stock>> findByProductId(String productId);
    Optional<List<Stock>> findByColorId(String id);
    Optional<List<Stock>> findBySizeId(String id);
    Optional<List<Stock>> findByColorName(String name);
    Optional<List<Stock>> findAllByColorName(String name);
    Optional<List<Stock>> findBySizeName(String name);

    Optional<List<Stock>> findByColorIdAndSizeId(String colorId, String sizeId);
}
