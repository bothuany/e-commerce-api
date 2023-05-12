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
public class UpdateSellerRequest {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String companyName;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String companyPhoneNumber;
}
