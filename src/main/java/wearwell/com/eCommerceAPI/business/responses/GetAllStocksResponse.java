package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wearwell.com.eCommerceAPI.entities.concretes.Color;
import wearwell.com.eCommerceAPI.entities.concretes.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStocksResponse {
    private String id;
    private String productID;
    private Color color;
    private Size size;
    private int quantity;
}
