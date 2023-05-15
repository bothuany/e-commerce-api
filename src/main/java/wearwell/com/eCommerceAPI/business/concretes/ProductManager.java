package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.ProductService;
import wearwell.com.eCommerceAPI.business.abstracts.StockService;
import wearwell.com.eCommerceAPI.business.requests.*;
import wearwell.com.eCommerceAPI.business.responses.*;
import wearwell.com.eCommerceAPI.business.rules.ProductBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.*;
import wearwell.com.eCommerceAPI.entities.concretes.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;
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
        GetByIdProductResponse productResponse = this.modelMapperService.forResponse().map(product, GetByIdProductResponse.class);

        productResponse.setCategory(this.modelMapperService.forResponse().map(product.getCategory(), GetByIdCategoryResponse.class));
        productResponse.setSeller(this.modelMapperService.forResponse().map(product.getSeller(), GetByIdSellerForProductsResponse.class));

        return productResponse;
    }

    @Override
    public GetByNameProductResponse getProductByName(String name) {
        Product product = this.productRepository.findBySlugName(name).orElseThrow();
        GetByIdProductResponse productResponse = getById(product.getId());
        List<GetAllStocksByProductResponse> stocks = stockService.getStocksByProductId(product.getId());

        return new GetByNameProductResponse(productResponse, stocks);
    }

    @Override
    public List<GetMyProductsResponse> getAllBySeller(String id) {
        Optional<List<Product>> products = this.productRepository.findAllBySellerId(id);
        List<GetMyProductsResponse> productResponses = new ArrayList<>();
        products.ifPresent(value -> value.forEach(product -> {
            GetMyProductsResponse productResponse = this.modelMapperService.forResponse().map(product, GetMyProductsResponse.class);
            List<GetAllStocksByProductResponse> stocks = stockService.getStocksByProductId(product.getId());
            productResponse.setStocks(stocks);
            productResponses.add(productResponse);
        }));

        return productResponses;
    }

    @Override
    public List<GetAllProductsResponseForSearch> search(String searchText, List<String> categories, List<String> colors, List<String> sizes, String sortBy) {
        List<Product> productsWithSearchTextAndCategoriesList = new ArrayList<>();
        List<Stock> stocksWithColorsList = new ArrayList<>();
        List<Stock> stocksWithSizesList = new ArrayList<>();
        List<Product> productsForColorsAndSizes = new ArrayList<>();

        if (categories.isEmpty()) {
            categories = categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
        }
        if (colors.isEmpty()) {
            colors = colorRepository.findAll().stream().map(Color::getName).collect(Collectors.toList());
        }
        if (sizes.isEmpty()) {
            sizes = sizeRepository.findAll().stream().map(Size::getName).collect(Collectors.toList());
        }

        for (String category : categories) {
            Optional<List<Product>> productsWithCategories = this.productRepository.searchAllByCategoryNameAndNameContainingIgnoreCase(category, searchText);
            productsWithCategories.ifPresent(productsWithSearchTextAndCategoriesList::addAll);
        }

        for (String color : colors) {
            stocksWithColorsList.addAll(stockService.filterByColorName(color));
        }

        for (String size : sizes) {
            stocksWithSizesList.addAll(stockService.filterBySizeName(size));
        }


        stocksWithColorsList.forEach(stock -> {
            if ((stock.getQuantity()>0)&&(stocksWithSizesList.contains(stock))) {
                if(!productsForColorsAndSizes.contains(stock.getProduct())){
                    productsForColorsAndSizes.add(stock.getProduct());
                }
            }
        });

        List<Product> products = new ArrayList<>();

        productsForColorsAndSizes.forEach(product -> {
            if (productsWithSearchTextAndCategoriesList.contains(product)) {
                products.add(product);
            }
        });


        if (sortBy.equals("HighToLow")) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else if (sortBy.equals("LowToHigh")) {
            products.sort(Comparator.comparing(Product::getPrice));
        }

        return products
                .stream()
                .map(model -> {
                    GetAllProductsResponseForSearch productResponse = this.modelMapperService
                            .forResponse()
                            .map(model, GetAllProductsResponseForSearch.class);
                    productResponse.setCompanyName(model.getSeller().getCompanyName());
                    return productResponse;
                })
                .collect(Collectors.toList());
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
