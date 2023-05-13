package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSizeRequest {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String name;
}
