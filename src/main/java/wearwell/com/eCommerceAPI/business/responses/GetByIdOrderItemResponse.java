package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdOrderItemResponse {
    private String id;
    private String orderID;
    private String stockID;
    private int quantity;
}
