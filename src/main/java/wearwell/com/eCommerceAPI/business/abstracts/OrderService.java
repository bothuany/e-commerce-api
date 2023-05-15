package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.ConfirmCartRequest;
import wearwell.com.eCommerceAPI.business.requests.CreateOrderRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateOrderRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllOrdersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdOrderResponse;
import wearwell.com.eCommerceAPI.business.responses.GetMyOrdersDetailedResponse;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

import java.util.List;

public interface OrderService {
    List<GetAllOrdersResponse> getAll();
    List<GetAllOrdersResponse> getAllByCustomerId(String customerId);
    List<GetMyOrdersDetailedResponse> getAllOrdersDetailedByCustomerId(String customerId);
    GetMyOrdersDetailedResponse getOrdersDetailedByCustomerId(String customerId,String id);
    GetByIdOrderResponse getById(String id);
    Order add(CreateOrderRequest createOrderRequest);
    Order update(UpdateOrderRequest updateOrderRequest);
    void delete(String id);

    String confirmCart(ConfirmCartRequest confirmCartRequest,String customerID);
    double calculateAmount(String id);


}
