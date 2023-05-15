package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.SellProductRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateStockRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksByProductResponse;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdStockResponse;
import wearwell.com.eCommerceAPI.entities.concretes.Product;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.List;

public interface StockService {
    List<GetAllStocksResponse> getAll();
    GetByIdStockResponse getById(String id);
    void add(CreateStockRequest createStockRequest);
    void update(UpdateStockRequest updateStockRequest);
    void delete(String id);
    void sellProduct(SellProductRequest sellProductRequest);
    List<GetAllStocksByProductResponse> getStocksBySellerId(String sellerId);
    List<GetAllStocksByProductResponse> getStocksByProductId(String productId);
    List<Stock> filterByColorId(String colorId);
    List<Stock> filterByColorName(String colorName);
    List<Stock> filterBySizeId(String sizeId);
    List<Stock> filterBySizeName(String sizeName);

    List<Stock> filterByColorIdAndSizeId(String colorId, String sizeId);


}
