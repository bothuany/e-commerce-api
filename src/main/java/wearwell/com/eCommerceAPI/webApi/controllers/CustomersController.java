package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.CustomerService;
import wearwell.com.eCommerceAPI.business.requests.CreateCustomerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCustomerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCustomersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCustomerResponse;
import wearwell.com.eCommerceAPI.entities.abstracts.User;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomersController {
    private CustomerService customerService;
    private AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<GetAllCustomersResponse>> getAll() {
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdCustomerResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthenticationResponse add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        return this.customerService.add(createCustomerRequest);
    }

    @PutMapping
    public ResponseEntity<UpdateCustomerRequest> update(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(updateCustomerRequest.getId())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.customerService.update(updateCustomerRequest);
            return new ResponseEntity<>(updateCustomerRequest, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(id)) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.customerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
