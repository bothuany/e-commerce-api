package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.InvoiceService;
import wearwell.com.eCommerceAPI.business.abstracts.OrderItemService;
import wearwell.com.eCommerceAPI.business.abstracts.OrderService;
import wearwell.com.eCommerceAPI.business.requests.*;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrderItemsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrdersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderResponse;
import wearwell.com.eCommerceAPI.business.responses.GetMyOrdersDetailedResponse;
import wearwell.com.eCommerceAPI.business.rules.OrderBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.CustomerRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.OrderItemRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.OrderRepository;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.StockRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;
import wearwell.com.eCommerceAPI.entities.concretes.Order;
import wearwell.com.eCommerceAPI.entities.concretes.OrderItem;
import wearwell.com.eCommerceAPI.entities.concretes.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderManager implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private CustomerRepository customerRepository;
    private StockRepository stockRepository;
    private ModelMapperService modelMapperService;
    private OrderItemService orderItemService;
    private InvoiceService invoiceService;
    private OrderBusinessRules orderBusinessRules;

    @Override
    public List<GetAllOrdersResponse> getAll() {

        List<Order> orders = orderRepository.findAll();

        return orders
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllOrdersResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetAllOrdersResponse> getAllByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            List<Order> orders = orderRepository.findAllByCustomer(customer.get());

            return orders
                    .stream()
                    .map(model -> this.modelMapperService
                            .forResponse()
                            .map(model, GetAllOrdersResponse.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<GetMyOrdersDetailedResponse> getAllOrdersDetailedByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            List<Order> orders = orderRepository.findAllByCustomer(customer.get());
            return orders.stream().map(order -> getOrdersDetailedByCustomerId(customerId, order.getId())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public GetMyOrdersDetailedResponse getOrdersDetailedByCustomerId(String customerId, String id) {
        GetByIdOrderResponse order = getById(id);
        List<GetAllOrderItemsResponse> orderItems = orderItemService.getByOrderId(order.getId());
        return new GetMyOrdersDetailedResponse(order, orderItems);
    }


    @Override
    public GetByIdOrderResponse getById(String id) {
        Order order = this.orderRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(order, GetByIdOrderResponse.class);
    }

    @Override
    public Order add(CreateOrderRequest createOrderRequest) {
        Order order = this.modelMapperService
                .forRequest()
                .map(createOrderRequest, Order.class);
        order.setId(null);

        return this.orderRepository.save(order);
    }

    @Override
    public Order update(UpdateOrderRequest updateOrderRequest) {
        Order order = this.modelMapperService
                .forRequest()
                .map(updateOrderRequest, Order.class);

        return this.orderRepository.save(order);
    }

    @Override
    public void delete(String id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public void confirmCart(ConfirmCartRequest confirmCartRequest, String customerID) {
        orderBusinessRules.checkInStock(confirmCartRequest.getOrderItems());

        CreateOrderRequest createOrderRequest = confirmCartRequest.getOrder();
        createOrderRequest.setCustomerID(customerID);

        Order order = add(createOrderRequest);

        for (CreateOrderItemRequest createOrderItemRequest : confirmCartRequest.getOrderItems()) {
            createOrderItemRequest.setOrderID(order.getId());
            orderItemService.add(createOrderItemRequest);
        }

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest(order.getId(), order.getOrderDate(), calculateAmount(order.getId()));
        invoiceService.add(createInvoiceRequest);
    }

    @Override
    public double calculateAmount(String id) {
        Optional<Order> order = orderRepository.findById(id);
        double amount = 0;

        if (order.isPresent()) {
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.get().getId());
            for (OrderItem orderItem : orderItems) {
                int quantity = orderItem.getQuantity();
                Optional<Stock> stock = stockRepository.findById(orderItem.getStock().getId());

                if (stock.isPresent()) {
                    double price = stock.get().getProduct().getPrice();
                    amount += quantity * price;
                }
            }
        }
        return amount;
    }
}
