package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreatePaymentRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdatePaymentRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllPaymentsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdPaymentResponse;

import java.util.List;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetByIdPaymentResponse getById(String id);
    void add(CreatePaymentRequest createPaymentRequest);
    void pay(CreatePaymentRequest createPaymentRequest);
    void update(UpdatePaymentRequest updatePaymentRequest);
    void delete(String id);
}
