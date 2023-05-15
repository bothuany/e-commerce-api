package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.business.abstracts.InvoiceService;
import wearwell.com.eCommerceAPI.business.abstracts.OrderService;
import wearwell.com.eCommerceAPI.business.requests.ConfirmCartRequest;
import wearwell.com.eCommerceAPI.business.requests.CreateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllInvoicesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdInvoiceResponse;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.entities.abstracts.User;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoicesController {
    private InvoiceService invoiceService;
    private OrderService orderService;
    private AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<GetAllInvoicesResponse>> getAll() {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(invoiceService.getAll(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdInvoiceResponse> getById(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(invoiceService.getById(id), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping()
    public ResponseEntity<CreateInvoiceRequest> add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId()
                .equals(orderService.getById(createInvoiceRequest.getOrderID()).getCustomerID()))
                || (activeUser.getRole().equals(Role.ADMIN))) {
            this.invoiceService.add(createInvoiceRequest);
            return new ResponseEntity<>(createInvoiceRequest, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<UpdateInvoiceRequest> update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId()
                .equals(orderService.getById(updateInvoiceRequest.getOrderID()).getCustomerID()))
                || (activeUser.getRole().equals(Role.ADMIN))) {
            this.invoiceService.update(updateInvoiceRequest);
            return new ResponseEntity<>(updateInvoiceRequest, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        User activeUser = authenticationService.activeUser();

        if ((activeUser.getId()
                .equals(orderService.getById(invoiceService.getById(id).getOrderID()).getCustomerID()))
                || (activeUser.getRole().equals(Role.ADMIN))) {
            this.invoiceService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/my-invoices")
    public ResponseEntity<List<GetAllInvoicesResponse>> getActiveCustomersInvoices() {
        String customerId = authenticationService.activeUser().getId();
        return new ResponseEntity<>(invoiceService.getAllByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("/my-invoices/{id}")
    public ResponseEntity<GetByIdInvoiceResponse> getActiveCustomersInvoices(@PathVariable String id) {
        String customerId = authenticationService.activeUser().getId();

        if ((customerId.equals(orderService.getById(invoiceService.getById(id).getOrderID()).getCustomerID()))) {
            return new ResponseEntity<>(invoiceService.getById(id), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/confirm-cart")
    public ResponseEntity<String> confirmCart(@RequestBody @Valid ConfirmCartRequest confirmCartRequest) {
        User activeUser = authenticationService.activeUser();

        return new ResponseEntity<>(this.orderService.confirmCart(confirmCartRequest, activeUser.getId()), HttpStatus.CREATED);


    }

}
