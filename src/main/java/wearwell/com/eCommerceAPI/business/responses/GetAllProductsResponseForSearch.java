package wearwell.com.eCommerceAPI.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wearwell.com.eCommerceAPI.entities.concretes.Category;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductsResponseForSearch {
    private String id;
    private String name;
    private String description;
    private List<String> highlights;
    private String details;
    private double price;
    private List<String> images;
    private Category category;
    private String companyName;
}