package com.victot.desafio_dev_jr_apse.service;

import com.victot.desafio_dev_jr_apse.model.Product;
import com.victot.desafio_dev_jr_apse.model.exception.NotFoundException;
import com.victot.desafio_dev_jr_apse.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        ArrayList<Product> products = new ArrayList<>(productRepository.findAll());
        if(!products.isEmpty()){
            return products;
        }else{
            throw new NotFoundException("Nenhum produto listado.");
        }
    }

    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new NotFoundException("Nenhum produto encontrado com esta id: " + id + ".");
        }else{
            return product;
        }
    }

    public Product save(Product product) {//todo verificar o que pode e o que não pode ser null usar verify no controller e na classe
        product.setCategory(product.getCategory().toLowerCase());
        return productRepository.save(product);
    }

    public Product update(Product product) {
        if(productRepository.existsById(product.getId())){
            Product old = productRepository.findById(product.getId()).orElse(null);
            old.setPrice(product.getPrice());//não será nulo pois o produto já foi verificado se existe antes do findById
            old.setCategory(product.getCategory());
            old.setDescription(product.getDescription());
            old.setName(product.getName());
            old.setDono(product.getDono());
            return productRepository.save(old);
        }else {
            throw new NotFoundException("O produto com esta id:" + product.getId() + " não foi encontrado, não é possível realizar o update.");
        }
    }

    public void delete(Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else{
            throw new NotFoundException("Nenhum produto encontrado com a id: " + id + ". Nada foi deletado.");
        }
    }
}
