package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.OrderService;
import wearwell.com.eCommerceAPI.business.requests.CreateOrderRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateOrderRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrdersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderResponse;
import wearwell.com.eCommerceAPI.business.responses.GetMyOrdersDetailedResponse;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.entities.abstracts.User;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrdersController {
    private OrderService orderService;
    private AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<GetAllOrdersResponse>> getAll() {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<GetMyOrdersDetailedResponse>> getActiveCustomersOrders() {
        String customerId = authenticationService.activeUser().getId();
        return new ResponseEntity<>(orderService.getAllOrdersDetailedByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("/my-orders/{id}")
    public ResponseEntity<GetMyOrdersDetailedResponse> getActiveCustomersOrderById(@PathVariable String id) {
        String customerId = authenticationService.activeUser().getId();
        return new ResponseEntity<>(orderService.getOrdersDetailedByCustomerId(customerId, id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdOrderResponse> getById(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping()
    public ResponseEntity<CreateOrderRequest> add(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(createOrderRequest.getCustomerID())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.orderService.add(createOrderRequest);
            return new ResponseEntity<>(createOrderRequest, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<UpdateOrderRequest> update(@RequestBody @Valid UpdateOrderRequest updateOrderRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(updateOrderRequest.getCustomerID())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.orderService.update(updateOrderRequest);
            return new ResponseEntity<>(updateOrderRequest, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId().equals(orderService.getById(id).getCustomerID())) || (activeUser.getRole().equals(Role.ADMIN))) {
            this.orderService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
