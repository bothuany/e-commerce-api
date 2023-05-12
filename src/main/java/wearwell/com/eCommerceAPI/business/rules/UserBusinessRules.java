package wearwell.com.eCommerceAPI.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.UserRepository;

@AllArgsConstructor
@Service
public class UserBusinessRules {
    private UserRepository userRepository;

    public void checkIfEmailExists(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException("Email already exists");
        }
    }
}