package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStockRequest {
    @NotNull
    @Min(1)
    @Max(10000)
    private int quantity;
    private String productID;
    private String colorID;
    private String sizeID;
}
