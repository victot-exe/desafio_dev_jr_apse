package com.victot.desafio_dev_jr_apse.controller;

import com.victot.desafio_dev_jr_apse.model.Product;
import com.victot.desafio_dev_jr_apse.model.User;
import com.victot.desafio_dev_jr_apse.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController @RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();


        product.setUpdatedBy(user);

        Product response = productService.save(product);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        product.setUpdatedBy(user);

        Product response = productService.update(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> response = productService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product response = productService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@RequestParam Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
