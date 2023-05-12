package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllInvoicesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrdersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdInvoiceResponse;
import wearwell.com.eCommerceAPI.entities.concretes.Invoice;

import java.util.List;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();

    GetByIdInvoiceResponse getByOrderId(String orderId);

    List<GetAllInvoicesResponse> getAllByCustomerId(String customerId);

    GetByIdInvoiceResponse getById(String id);

    void add(CreateInvoiceRequest createInvoiceRequest);

    void update(UpdateInvoiceRequest updateInvoiceRequest);

    void delete(String id);


}
