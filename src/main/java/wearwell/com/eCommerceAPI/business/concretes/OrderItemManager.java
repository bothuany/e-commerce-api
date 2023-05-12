package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.OrderItemService;
import wearwell.com.eCommerceAPI.business.requests.CreateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrderItemsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderItemResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.OrderItemRepository;
import wearwell.com.eCommerceAPI.entities.concretes.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemManager implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllOrderItemsResponse> getAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();

        return orderItems
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllOrderItemsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdOrderItemResponse getById(String id) {
        OrderItem orderItem = this.orderItemRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(orderItem, GetByIdOrderItemResponse.class);
    }

    @Override
    public List<GetAllOrderItemsResponse> getByOrderId(String orderId) {
        return orderItemRepository.findAllByOrderId(orderId).stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllOrderItemsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void add(CreateOrderItemRequest createOrderItemRequest) {
        OrderItem orderItem = this.modelMapperService
                .forRequest()
                .map(createOrderItemRequest, OrderItem.class);

        this.orderItemRepository.save(orderItem);
    }

    @Override
    public void update(UpdateOrderItemRequest updateOrderItemRequest) {
        OrderItem orderItem = this.modelMapperService
                .forRequest()
                .map(updateOrderItemRequest, OrderItem.class);

        this.orderItemRepository.save(orderItem);
    }

    @Override
    public void delete(String id) {
        this.orderItemRepository.deleteById(id);
    }
}
