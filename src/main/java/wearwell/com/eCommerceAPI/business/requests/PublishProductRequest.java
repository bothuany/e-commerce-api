package wearwell.com.eCommerceAPI.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishProductRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String description;

    private List<String> highlights;

    private String details;

    @NotNull
    private double price;

    @NotNull
    private List<String> images;

    @NotNull
    @NotBlank
    private String categoryID;

}
