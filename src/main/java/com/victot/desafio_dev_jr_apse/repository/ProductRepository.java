package com.victot.desafio_dev_jr_apse.repository;

import com.victot.desafio_dev_jr_apse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

    List<Product> findByCategory(String category);
}
