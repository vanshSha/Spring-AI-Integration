package metadata_filter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;


@Data
@ToString
public class Product {
    private int id;

    @JsonProperty("product_name")
    private String productName;

    private String description;

    private String brand;

    private Double price;

    private String year;

    private String county;

    @JsonProperty("isAvailable")
    private boolean isAvailable;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(int id, String productName, String description, String brand, Double price, String year, String county,boolean isAvailable) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.year = year;
        this.county = county;
        this.isAvailable = isAvailable;
    }

    // Copy Constructor
    public Product(Product other) {
        this.id = other.id;
        this.productName = other.productName;
        this.description = other.description;
        this.brand = other.brand;
        this.price = other.price;
        this.year = other.year;
        this.county = other.county;
        this.isAvailable = other.isAvailable;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(description, product.description) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(price, product.price) &&
                Objects.equals(year, product.year) &&
                Objects.equals(county, product.county) &&
                Objects.equals(isAvailable, product.isAvailable);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, productName, description, brand, price, year, county, isAvailable);
    }

    // Builder pattern method for fluent API (optional)
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    // Inner Builder class (optional)
    public static class ProductBuilder {
        private final Product product;

        ProductBuilder() {
            product = new Product();
        }

        public ProductBuilder id(int id) {
            product.setId(id);
            return this;
        }

        public ProductBuilder productName(String productName) {
            product.setProductName(productName);
            return this;
        }

        public ProductBuilder description(String description) {
            product.setDescription(description);
            return this;
        }

        public ProductBuilder brand(String brand) {
            product.setBrand(brand);
            return this;
        }

        public Product build() {
            return new Product(product);
        }
    }
}