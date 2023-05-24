package com.co.parrolabs;

import com.co.parrolabs.repositories.ProductRepository;
import com.co.parrolabs.repositories.entities.Product;
import com.co.parrolabs.services.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testCreateProduct_ShouldReturnSavedProduct() {
        // Mock data
        Product product = new Product(1L, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Perform the test
        Optional<Product> result = productService.createProduct(product);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
        assertEquals(product.getDescription(), result.get().getDescription());
        assertEquals(product.getPrice(), result.get().getPrice());
        assertEquals(product.getWeight(), result.get().getWeight());
    }

    @Test
    public void testGetProductById_WithExistingId_ShouldReturnProduct() {
        // Mock data
        long productId = 1L;
        Product product = new Product(productId, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Perform the test
        Optional<Product> result = productService.getProductById(productId);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
        assertEquals(product.getDescription(), result.get().getDescription());
        assertEquals(product.getPrice(), result.get().getPrice());
        assertEquals(product.getWeight(), result.get().getWeight());
    }

    @Test
    public void testGetProductById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Mock data
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Perform the test
        Optional<Product> result = productService.getProductById(productId);

        // Assertions
        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllProducts_ShouldReturnAllProducts() {
        // Mock data
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0)));
        productList.add(new Product(2L, "Product B", BigDecimal.valueOf(20.0), BigDecimal.valueOf(2.0)));
        when(productRepository.findAll()).thenReturn(productList);

        // Perform the test
        List<Product> result = productService.getAllProducts();

        // Assertions
        assertEquals(productList.size(), result.size());
        for (int i = 0; i < productList.size(); i++) {
            assertEquals(productList.get(i).getId(), result.get(i).getId());
            assertEquals(productList.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(productList.get(i).getPrice(), result.get(i).getPrice());
            assertEquals(productList.get(i).getWeight(), result.get(i).getWeight());
        }
    }

    @Test
    public void testUpdateProduct_WithExistingId_ShouldReturnUpdatedProduct() {
        // Mock data
        long productId = 1L;
        Product existingProduct = new Product(productId, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0));
        Product updatedProduct = new Product(productId, "Product B", BigDecimal.valueOf(20.0), BigDecimal.valueOf(2.0));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Perform the test
        Optional<Product> result = productService.updateProduct(productId, updatedProduct);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(updatedProduct.getId(), result.get().getId());
        assertEquals(updatedProduct.getDescription(), result.get().getDescription());
        assertEquals(updatedProduct.getPrice(), result.get().getPrice());
        assertEquals(updatedProduct.getWeight(), result.get().getWeight());
    }

    @Test
    public void testUpdateProduct_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Mock data
        long productId = 1L;
        Product updatedProduct = new Product(productId, "Product B", BigDecimal.valueOf(20.0), BigDecimal.valueOf(2.0));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Perform the test
        Optional<Product> result = productService.updateProduct(productId, updatedProduct);

        // Assertions
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteProduct_WithExistingId_ShouldReturnTrue() {
        // Mock data
        long productId = 1L;
        Product existingProduct = new Product(productId, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Perform the test
        boolean result = productService.deleteProduct(productId);

        // Assertions
        assertTrue(result);
        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    public void testDeleteProduct_WithNonExistingId_ShouldReturnFalse() {
        // Mock data
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Perform the test
        boolean result = productService.deleteProduct(productId);

        // Assertions
        assertFalse(result);
        verify(productRepository, never()).delete(any(Product.class));
    }

}
