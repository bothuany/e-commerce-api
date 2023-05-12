package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateProductRequest;
import wearwell.com.eCommerceAPI.business.requests.PutOnSaleRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateProductRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllProductsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdProductResponse;

import java.util.List;

public interface ProductService {
    List<GetAllProductsResponse> getAll();
    GetByIdProductResponse getById(String id);
    void add(CreateProductRequest createProductRequest);
    void putOnSale(PutOnSaleRequest putOnSaleRequest, String sellerId);
    void update(UpdateProductRequest updateProductRequest);
    void delete(String id);
}
