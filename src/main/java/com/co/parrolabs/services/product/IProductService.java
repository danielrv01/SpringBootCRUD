package com.co.parrolabs.services.product;

import com.co.parrolabs.repositories.entities.Product;

import java.util.List;
import java.util.Optional;


public interface IProductService {

    Optional<Product> createProduct(Product product);

    Optional<Product> getProductById(Long id);


    List<Product> getAllProducts();

    Optional<Product> updateProduct(Long id, Product updatedProduct);

    boolean deleteProduct(Long id);

}
