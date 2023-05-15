package wearwell.com.eCommerceAPI.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    @Column(name = "slugName")
    private String slugName;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void generateSlugName() {
        this.slugName = slugify(this.name);
    }

    public static String slugify(String text) {
        Pattern WHITESPACE = Pattern.compile("[\\s]");
        String nowhitespace = WHITESPACE.matcher(text).replaceAll("-");
        String slug = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String normalizedText = Normalizer.normalize(slug, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String replacedText = pattern.matcher(normalizedText)
                .replaceAll("")
                .replaceAll("ç", "c")
                .replaceAll("ş", "s")
                .replaceAll("ğ", "g")
                .replaceAll("ü", "u")
                .replaceAll("ı", "i")
                .replaceAll("ö", "o")
                .replaceAll("Ç", "C")
                .replaceAll("Ş", "S")
                .replaceAll("Ğ", "G")
                .replaceAll("Ü", "U")
                .replaceAll("İ", "I")
                .replaceAll("Ö", "O");

        return replacedText.toLowerCase();
    }


}
