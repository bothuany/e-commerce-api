package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.ProductService;
import wearwell.com.eCommerceAPI.business.abstracts.StockService;
import wearwell.com.eCommerceAPI.business.requests.CreateProductRequest;
import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.PutOnSaleRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateProductRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllProductsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdProductResponse;
import wearwell.com.eCommerceAPI.business.rules.ProductBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.ProductRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.SellerRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Product;
import wearwell.com.eCommerceAPI.entities.concretes.Seller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private StockService stockService;
    private ProductBusinessRules productBusinessRules;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllProductsResponse> getAll() {
        List<Product> product = productRepository.findAll();

        return product
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllProductsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdProductResponse getById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(product, GetByIdProductResponse.class);
    }

    @Override
    public void add(CreateProductRequest createProductRequest) {
        productBusinessRules.checkIfNameExists(createProductRequest.getName());

        Product product = this.modelMapperService
                .forRequest()
                .map(createProductRequest, Product.class);

        this.productRepository.save(product);
    }

    @Override
    public void putOnSale(PutOnSaleRequest putOnSaleRequest, String sellerId) {
        productBusinessRules.checkIfNameExists(putOnSaleRequest.getProduct().getName());

        Product product = this.modelMapperService
                .forRequest()
                .map(putOnSaleRequest.getProduct(), Product.class);

        Optional<Seller> seller = sellerRepository.findById(sellerId);

        if (seller.isPresent()) {
            product.setSeller(seller.get());
            product = this.productRepository.save(product);
        } else {
            throw new BusinessException("Seller is not found");
        }

        for (CreateStockRequest createStockRequest : putOnSaleRequest.getStocks()) {
            createStockRequest.setProductID(product.getId());
            stockService.add(createStockRequest);
        }


    }

    @Override
    public void update(UpdateProductRequest updateProductRequest) {
        productBusinessRules.checkIfNameExists(updateProductRequest.getName());

        Product product = this.modelMapperService
                .forRequest()
                .map(updateProductRequest, Product.class);

        this.productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        this.productRepository.deleteById(id);
    }


}
