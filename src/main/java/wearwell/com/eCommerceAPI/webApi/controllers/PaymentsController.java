package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.PaymentService;
import wearwell.com.eCommerceAPI.business.requests.CreatePaymentRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdatePaymentRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllPaymentsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdPaymentResponse;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentsController {
    private PaymentService paymentService;

    @GetMapping()
    public ResponseEntity<List<GetAllPaymentsResponse>> getAll() {
        return new ResponseEntity<>(paymentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdPaymentResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(paymentService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreatePaymentRequest> add(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        this.paymentService.add(createPaymentRequest);
        return new ResponseEntity<>(createPaymentRequest, HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<CreatePaymentRequest> pay(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        this.paymentService.pay(createPaymentRequest);
        return new ResponseEntity<>(createPaymentRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdatePaymentRequest> update(@RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) {
        this.paymentService.update(updatePaymentRequest);
        return new ResponseEntity<>(updatePaymentRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
