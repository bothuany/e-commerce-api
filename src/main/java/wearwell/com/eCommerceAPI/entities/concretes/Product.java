package wearwell.com.eCommerceAPI.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "products")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "highlights")
    @Column(name = "highlights")
    private List<String> highlights = new ArrayList<>();

    @Column(name = "details")
    private String details;

    @Column(name = "price")
    private double price;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "images")
    @Column(name = "images")
    private List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sellerID", referencedColumnName = "id")
    private Seller seller;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", highlights=" + highlights +
                ", details='" + details + '\'' +
                ", price=" + price +
                ", images=" + images +
                ", category=" + category +
                ", seller=" + seller +
                '}';
    }
}
