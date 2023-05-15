package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateProductRequest;
import wearwell.com.eCommerceAPI.business.requests.PutOnSaleRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateProductRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSaleRequest;
import wearwell.com.eCommerceAPI.business.responses.*;

import java.util.List;

public interface ProductService {
    List<GetAllProductsResponse> getAll();
    GetByIdProductResponse getById(String id);
    GetByNameProductResponse getProductByName(String name);
    List<GetMyProductsResponse> getAllBySeller(String id);
    List<GetAllProductsResponseForSearch> search(String searchText, List<String> categories, List<String> colors, List<String> sizes,String sortBy);
    void add(CreateProductRequest createProductRequest);
    void putOnSale(PutOnSaleRequest putOnSaleRequest, String sellerId);
    void update(UpdateProductRequest updateProductRequest);
    void delete(String id);

}
