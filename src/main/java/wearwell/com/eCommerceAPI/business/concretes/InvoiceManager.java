package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.InvoiceService;
import wearwell.com.eCommerceAPI.business.requests.CreateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateInvoiceRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllInvoicesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdInvoiceResponse;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.CustomerRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.InvoiceRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.OrderRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;
import wearwell.com.eCommerceAPI.entities.concretes.Invoice;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllInvoicesResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdInvoiceResponse getByOrderId(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            Invoice invoice = invoiceRepository.findByOrder(order.get());

            return this.modelMapperService
                    .forResponse()
                    .map(invoice, GetByIdInvoiceResponse.class);
        }
        throw new BusinessException("Invoice is not found");
    }

    @Override
    public List<GetAllInvoicesResponse> getAllByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            List<Order> orders = orderRepository.findAllByCustomer(customer.get());

            List<Invoice> invoices = orders.stream().map((order) -> invoiceRepository.findByOrder(order)).toList();

            return invoices
                    .stream()
                    .map(model -> this.modelMapperService
                            .forResponse()
                            .map(model, GetAllInvoicesResponse.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public GetByIdInvoiceResponse getById(String id) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(invoice, GetByIdInvoiceResponse.class);
    }

    @Override
    public void add(CreateInvoiceRequest createInvoiceRequest) {
        Invoice invoice = this.modelMapperService
                .forRequest()
                .map(createInvoiceRequest, Invoice.class);

        this.invoiceRepository.save(invoice);
    }

    @Override
    public void update(UpdateInvoiceRequest updateInvoiceRequest) {
        Invoice invoice = this.modelMapperService
                .forRequest()
                .map(updateInvoiceRequest, Invoice.class);

        this.invoiceRepository.save(invoice);
    }

    @Override
    public void delete(String id) {
        this.invoiceRepository.deleteById(id);
    }
}
