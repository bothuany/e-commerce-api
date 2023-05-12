package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrdersResponse {
    private String id;
    private String customerID;
    private boolean isPaid;
    private Date orderDate;
    private String status;
    private String address;
    private String phoneNumber;
}
