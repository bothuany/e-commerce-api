package wearwell.com.eCommerceAPI.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.requests.CreateOrderItemRequest;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.StockRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderBusinessRules {
    private StockRepository stockRepository;

    public void checkInStock(String stockId, int quantity) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if (stock.isPresent()) {
            if (stock.get().getQuantity() < quantity) {
                throw new BusinessException("There is not enough products in stock.");
            } else return;
        }

        throw new BusinessException("Stock information is not found.");

    }

    public void checkInStock(List<CreateOrderItemRequest> orderItemRequests) {
        for (CreateOrderItemRequest orderItemRequest : orderItemRequests) {
            checkInStock(orderItemRequest.getStockID(), orderItemRequest.getQuantity());
        }
    }
}