package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.requests.*;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UsersController {

    private ModelMapperService modelMapperService;
    private AdminsController adminsController;
    private SellersController sellersController;
    private CustomersController customersController;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        switch (registerRequest.getRole()) {

            case "admin" -> {
                CreateAdminRequest createAdminRequest = this.modelMapperService
                        .forRequest()
                        .map(registerRequest, CreateAdminRequest.class);

                return adminsController.add(createAdminRequest);
            }

            case "customer" -> {
                CreateCustomerRequest createCustomerRequest = this.modelMapperService
                        .forRequest()
                        .map(registerRequest, CreateCustomerRequest.class);
                return customersController.add(createCustomerRequest);
            }
            case "seller" -> {
                CreateSellerRequest createSellerRequest = this.modelMapperService
                        .forRequest()
                        .map(registerRequest, CreateSellerRequest.class);
                return sellersController.add(createSellerRequest);
            }
        }
        return new AuthenticationResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
