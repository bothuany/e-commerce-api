package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByNameProductResponse {
    private GetByIdProductResponse product;
    private List<GetAllStocksByProductResponse> stocks;
}
