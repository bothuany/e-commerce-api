package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockRequest {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String productID;

    @NotNull
    @NotBlank
    private String colorID;

    @NotNull
    @NotBlank
    private String sizeID;

    @NotNull
    @NotBlank
    @Size(min = 10,max = 200)
    private int quantity;
}
