package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String password;
}
