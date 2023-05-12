package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.SellProductRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateStockRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdStockResponse;

import java.util.List;

public interface StockService {
    List<GetAllStocksResponse> getAll();
    GetByIdStockResponse getById(String id);
    void add(CreateStockRequest createStockRequest);
    void update(UpdateStockRequest updateStockRequest);
    void delete(String id);
    void sellProduct(SellProductRequest sellProductRequest);
}
