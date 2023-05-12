package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductsResponse {
    private String id;
    private String name;
    private String description;
    private List<String> highlights;
    private String details;
    private double price;
    private List<String> images;
    private String categoryID;
    //private Category category;
    private String sellerID;
    //private Seller seller;
}
