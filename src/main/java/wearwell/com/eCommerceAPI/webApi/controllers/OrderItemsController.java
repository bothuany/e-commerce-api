package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.OrderItemService;
import wearwell.com.eCommerceAPI.business.requests.CreateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrderItemsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderItemResponse;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@AllArgsConstructor
public class OrderItemsController {
    private OrderItemService orderItemService;

    @GetMapping()
    public ResponseEntity<List<GetAllOrderItemsResponse>> getAll() {
        return new ResponseEntity<>(orderItemService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdOrderItemResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(orderItemService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateOrderItemRequest> add(@RequestBody @Valid CreateOrderItemRequest createOrderItemRequest) {
        this.orderItemService.add(createOrderItemRequest);
        return new ResponseEntity<>(createOrderItemRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateOrderItemRequest> update(@RequestBody @Valid UpdateOrderItemRequest updateOrderItemRequest) {
        this.orderItemService.update(updateOrderItemRequest);
        return new ResponseEntity<>(updateOrderItemRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.orderItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
