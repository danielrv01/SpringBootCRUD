package com.co.parrolabs.controller.product;

import com.co.parrolabs.repositories.entities.Product;
import com.co.parrolabs.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Optional<Product> createdProduct = productService.createProduct(productDTO.toEntity());

        return createdProduct.map(p -> ResponseEntity.ok(ProductDTO.fromEntity(p)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);

        return product.map(p -> ResponseEntity.ok(ProductDTO.fromEntity(p)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> productDTOs = productService.getAllProducts();
        List<ProductDTO> customerDTOs = productDTOs.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDTO.toEntity());

        return updatedProduct.map(p -> ResponseEntity.ok(ProductDTO.fromEntity(p)))
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
