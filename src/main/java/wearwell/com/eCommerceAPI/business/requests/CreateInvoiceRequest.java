package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
    @NotNull
    @NotBlank
    private String orderID;

    private Date invoiceDate;
    @NotNull
    @NotBlank
    private double amount;
}
