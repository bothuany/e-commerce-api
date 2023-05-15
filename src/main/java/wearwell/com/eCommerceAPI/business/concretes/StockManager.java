package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.StockService;
import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.SellProductRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateStockRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksByProductResponse;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdStockResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.ColorRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.StockRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Product;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockManager implements StockService {
    private StockRepository stockRepository;
    private ColorRepository colorRepository;
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

    @Override
    public List<GetAllStocksByProductResponse> getStocksBySellerId(String sellerId) {
         List<Stock> stocks = stockRepository.findAll().stream().filter((stock) -> stock.getProduct().getSeller().getId().equals(sellerId)).collect(Collectors.toList());

            return stocks
                    .stream()
                    .map(model -> this.modelMapperService
                            .forResponse()
                            .map(model, GetAllStocksByProductResponse.class))
                    .collect(Collectors.toList());
    }

    @Override
    public List<GetAllStocksByProductResponse> getStocksByProductId(String productId) {
        Optional<List<Stock>> stocks = stockRepository.findByProductId(productId);

        return stocks.map(stockList -> stockList
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllStocksByProductResponse.class))
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<Stock> filterByColorId(String colorId) {
        Optional<List<Stock>> stocks = stockRepository.findByColorId(colorId);

        return stocks.orElseGet(ArrayList::new);
    }

    @Override
    public List<Stock> filterByColorName(String colorName) {
        Optional<List<Stock>> stocks = stockRepository.findByColorName(colorName);

        return stocks.orElseGet(ArrayList::new);
    }

    @Override
    public List<Stock> filterBySizeId(String sizeId) {
        Optional<List<Stock>> stocks = stockRepository.findBySizeId(sizeId);

        return stocks.orElseGet(ArrayList::new);
    }

    @Override
    public List<Stock> filterBySizeName(String sizeName) {
        Optional<List<Stock>> stocks = stockRepository.findBySizeName(sizeName);

        return stocks.orElseGet(ArrayList::new);
    }

    @Override
    public List<Stock> filterByColorIdAndSizeId(String colorId, String sizeId) {
        Optional<List<Stock>> stocks = stockRepository.findByColorIdAndSizeId(colorId, sizeId);

        return stocks.orElseGet(ArrayList::new);
    }
}
