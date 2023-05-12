package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.ProductService;
import wearwell.com.eCommerceAPI.business.requests.CreateProductRequest;
import wearwell.com.eCommerceAPI.business.requests.PutOnSaleRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateProductRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllProductsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdProductResponse;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.entities.abstracts.User;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductsController {
    private ProductService productService;
    private AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<GetAllProductsResponse>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdProductResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateProductRequest> add(@RequestBody @Valid CreateProductRequest createProductRequest) {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            this.productService.add(createProductRequest);
            return new ResponseEntity<>(createProductRequest, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/put-on-sale")
    public ResponseEntity<PutOnSaleRequest> putOnSale(@RequestBody @Valid PutOnSaleRequest putOnSaleRequest) {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.SELLER)) {
            this.productService.putOnSale(putOnSaleRequest, activeUser.getId());
            return new ResponseEntity<>(putOnSaleRequest, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<UpdateProductRequest> update(@RequestBody @Valid UpdateProductRequest updateProductRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(updateProductRequest.getSellerID())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.productService.update(updateProductRequest);
            return new ResponseEntity<>(updateProductRequest, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(productService.getById(id).getSellerID())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.productService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
