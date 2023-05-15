package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Color;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, String> {
    Optional<Color> findByName(String name);
}
