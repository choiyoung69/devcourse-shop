package com.example.shop.product.infrastructure;

import com.example.shop.product.domain.Product;
import com.example.shop.product.domain.ProductRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    @Autowired
    public ProductJpaRepository productJpaRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(UUID uuid) {
        return productJpaRepository.findById(uuid);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public void deleteById(UUID uuid) {
        productJpaRepository.deleteById(uuid);
    }
}
