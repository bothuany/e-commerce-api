package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdPaymentResponse {
    private String id;
    private String orderID;
    private String paymentMethod;
    private int amount;
}
