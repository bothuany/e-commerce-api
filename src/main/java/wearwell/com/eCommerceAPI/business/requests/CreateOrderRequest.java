package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String status="NOT READY";
    private Date orderDate = new Date();
    private boolean isPaid = false;

    @NotNull
    @NotBlank
    @Size(min = 1,max = 200)
    private String address;

    @NotNull
    @NotBlank
    @Size(min = 10,max = 11)
    private String phoneNumber;

    private String customerID;
}
