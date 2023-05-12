package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String paymentMethod;

    @Min(0)
    private double amount;

    @NotNull
    @NotBlank
    private String orderID;
}
