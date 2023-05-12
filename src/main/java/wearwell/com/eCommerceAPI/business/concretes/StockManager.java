package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.StockService;
import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.SellProductRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateStockRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdStockResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.StockRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockManager implements StockService {
    private StockRepository stockRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllStocksResponse> getAll() {
        List<Stock> stock = stockRepository.findAll();

        return stock
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllStocksResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdStockResponse getById(String id) {
        Stock stock = this.stockRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(stock, GetByIdStockResponse.class);
    }

    @Override
    public void add(CreateStockRequest createStockRequest) {
        Stock stock = this.modelMapperService
                .forRequest()
                .map(createStockRequest, Stock.class);

        this.stockRepository.save(stock);
    }

    @Override
    public void update(UpdateStockRequest updateStockRequest) {
        Stock stock = this.modelMapperService
                .forRequest()
                .map(updateStockRequest, Stock.class);

        this.stockRepository.save(stock);
    }

    @Override
    public void delete(String id) {
        this.stockRepository.deleteById(id);
    }

    @Override
    public void sellProduct(SellProductRequest sellProductRequest) {
        Optional<Stock> stockOptional = stockRepository.findById(sellProductRequest.getStockId());

        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            int availableQuantity = stock.getQuantity();

            if (availableQuantity >= sellProductRequest.getAmount()) {
                int updatedQuantity = availableQuantity - sellProductRequest.getAmount();
                stock.setQuantity(updatedQuantity);
            }
        }
    }
}
