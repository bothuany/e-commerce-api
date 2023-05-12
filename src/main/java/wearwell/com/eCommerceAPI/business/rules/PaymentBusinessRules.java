package wearwell.com.eCommerceAPI.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.auth.AuthenticationService;
import wearwell.com.eCommerceAPI.core.utilities.exceptions.BusinessException;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.OrderRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Order;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PaymentBusinessRules {
    private OrderRepository orderRepository;
    private AuthenticationService authenticationService;

    public void checkIfItIsMe(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            if (!order.get().getCustomer().getId().equals(authenticationService.activeUser().getId())) {
                throw new BusinessException("You can only buy from your own account");
            } else return;
        }

        throw new BusinessException("Order information is not found.");

    }

    public void isAlreadyPaid(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            if (order.get().isPaid()) {
                throw new BusinessException("You already paid for this order");
            } else return;
        }

        throw new BusinessException("Order information is not found.");

    }

}