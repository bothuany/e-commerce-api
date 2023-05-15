package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wearwell.com.eCommerceAPI.entities.concretes.Color;
import wearwell.com.eCommerceAPI.entities.concretes.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStocksByProductResponse {
    private String id;
    private Color color;
    private Size size;
    private int quantity;
}

