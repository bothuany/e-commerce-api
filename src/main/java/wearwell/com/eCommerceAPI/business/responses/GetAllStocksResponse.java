package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStocksResponse {
    private String id;
    private String productID;
    private String colorID;
    private String sizeID;
    private int quantity;
}
