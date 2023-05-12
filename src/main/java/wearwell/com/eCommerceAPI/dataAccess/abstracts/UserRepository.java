package wearwell.com.eCommerceAPI.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import wearwell.com.eCommerceAPI.entities.abstracts.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
