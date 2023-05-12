package wearwell.com.eCommerceAPI.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.ProductRepository;

@AllArgsConstructor
@Service
public class ProductBusinessRules {
    private ProductRepository productRepository;

    public void checkIfNameExists(String name) {
        if (this.productRepository.existsByName(name)) {
            throw new BusinessException("Name already exists");
        }
    }
}