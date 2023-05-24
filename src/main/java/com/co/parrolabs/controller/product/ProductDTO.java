package com.co.parrolabs.controller.product;

import com.co.parrolabs.repositories.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private Long id;
    private String description;
    private BigDecimal price;
    private BigDecimal weight;

    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setWeight(this.weight);
        return product;
    }

    public static ProductDTO fromEntity(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        return productDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, weight);
    }
}

