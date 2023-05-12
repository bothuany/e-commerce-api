package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdSellerResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String companyPhoneNumber;
}
