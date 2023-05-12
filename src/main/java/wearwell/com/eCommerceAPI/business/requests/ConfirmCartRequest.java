package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmCartRequest {
    @NotNull
    private List<CreateOrderItemRequest> orderItems;

    @NotNull
    private CreateOrderRequest order;
}
