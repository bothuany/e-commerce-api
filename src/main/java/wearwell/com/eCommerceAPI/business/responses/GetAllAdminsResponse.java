package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAdminsResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}
