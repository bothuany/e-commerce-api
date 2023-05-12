package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {
    @NotNull
    @NotBlank
    private String id;

    private String customerID;

    private boolean isPaid;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date orderDate;

    private String status;

    @Size(min = 1,max = 200)
    private String address;

    @Size(min = 10,max = 11)
    private String phoneNumber;

}
