package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String role;

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


    @Size(min = 3,max = 35)
    private String companyName;


    @Size(min = 3,max = 35)
    private String companyPhoneNumber;
}
