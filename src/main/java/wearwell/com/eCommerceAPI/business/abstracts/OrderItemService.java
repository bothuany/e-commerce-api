package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateOrderItemRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrderItemsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderItemResponse;

import java.util.List;

public interface OrderItemService {
    List<GetAllOrderItemsResponse> getAll();
    GetByIdOrderItemResponse getById(String id);
    List<GetAllOrderItemsResponse> getByOrderId(String orderId);
    void add(CreateOrderItemRequest createOrderItemRequest);
    void update(UpdateOrderItemRequest updateOrderItemRequest);
    void delete(String id);

}
