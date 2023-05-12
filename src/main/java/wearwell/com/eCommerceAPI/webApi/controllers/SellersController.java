package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.SellerService;
import wearwell.com.eCommerceAPI.business.requests.CreateSellerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSellerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSellersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSellerResponse;
import wearwell.com.eCommerceAPI.entities.abstracts.User;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@AllArgsConstructor
public class SellersController {
    private SellerService sellerService;
    private AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<GetAllSellersResponse>> getAll() {
        return new ResponseEntity<>(sellerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdSellerResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(sellerService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthenticationResponse add(@RequestBody @Valid CreateSellerRequest createSellerRequest) {
        return this.sellerService.add(createSellerRequest);
    }

    @PutMapping
    public ResponseEntity<UpdateSellerRequest> update(@RequestBody @Valid UpdateSellerRequest updateSellerRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(updateSellerRequest.getId())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.sellerService.update(updateSellerRequest);
            return new ResponseEntity<>(updateSellerRequest, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(id)) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.sellerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
