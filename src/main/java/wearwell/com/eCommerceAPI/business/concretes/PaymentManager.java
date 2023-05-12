package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.PaymentService;
import wearwell.com.eCommerceAPI.business.requests.CreatePaymentRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdatePaymentRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllPaymentsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdPaymentResponse;
import wearwell.com.eCommerceAPI.business.rules.PaymentBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.*;
import wearwell.com.eCommerceAPI.entities.concretes.Order;
import wearwell.com.eCommerceAPI.entities.concretes.OrderItem;
import wearwell.com.eCommerceAPI.entities.concretes.Payment;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private StockRepository stockRepository;
    private InvoiceRepository invoiceRepository;
    private ModelMapperService modelMapperService;
    private PaymentBusinessRules paymentBusinessRules;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payment = paymentRepository.findAll();

        return payment
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllPaymentsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdPaymentResponse getById(String id) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(payment, GetByIdPaymentResponse.class);
    }

    @Override
    public void add(CreatePaymentRequest createPaymentRequest) {
        Payment payment = this.modelMapperService
                .forRequest()
                .map(createPaymentRequest, Payment.class);

        this.paymentRepository.save(payment);
    }

    @Override
    public void pay(CreatePaymentRequest createPaymentRequest) {
        String orderId = createPaymentRequest.getOrderID();
        paymentBusinessRules.checkIfItIsMe(orderId);
        paymentBusinessRules.isAlreadyPaid(orderId);

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order paidOrder = order.get();
            createPaymentRequest.setAmount(invoiceRepository.findByOrder(paidOrder).getAmount());
            paidOrder.setPaid(true);
            orderRepository.save(paidOrder);

            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                Stock stock = orderItem.getStock();
                stock.setQuantity(stock.getQuantity() - orderItem.getQuantity());
                stockRepository.save(stock);
            }
        }
        Payment payment = this.modelMapperService
                .forRequest()
                .map(createPaymentRequest, Payment.class);
        this.paymentRepository.save(payment);
    }

    @Override
    public void update(UpdatePaymentRequest updatePaymentRequest) {
        Payment payment = this.modelMapperService
                .forRequest()
                .map(updatePaymentRequest, Payment.class);

        this.paymentRepository.save(payment);
    }

    @Override
    public void delete(String id) {
        this.paymentRepository.deleteById(id);
    }
}
