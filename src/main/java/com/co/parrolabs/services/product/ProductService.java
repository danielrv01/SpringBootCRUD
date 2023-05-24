package com.co.parrolabs.services.product;

import com.co.parrolabs.repositories.entities.Product;
import com.co.parrolabs.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Optional<Product> createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return Optional.ofNullable(savedProduct);
    }
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            updatedProduct.setId(id);
            Product savedProduct = productRepository.save(updatedProduct);
            return Optional.of(savedProduct);
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        } else {
            return false;
        }
    }
}
