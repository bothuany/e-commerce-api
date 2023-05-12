package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdInvoiceResponse {
    private String id;
    private String orderID;
    private double amount;
    private Date invoiceDate;
}
