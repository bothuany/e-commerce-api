package wearwell.com.eCommerceAPI.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSaleRequest {
    private String id;
    private PutOnSaleRequest product;
}
