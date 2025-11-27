package com.example.shop.product.application;

import com.example.shop.product.application.dto.ProductCommand;
import com.example.shop.product.application.dto.ProductInfo;
import com.example.shop.product.domain.Product;
import com.example.shop.product.domain.ProductRepository;
import com.example.shop.product.infrastructure.ProductJpaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    public List<ProductInfo> findAll(Pageable pageable) {
        Page<Product> pages = productRepository.findAll(pageable);
        return pages.stream()
                .map(ProductInfo::from)
                .toList();
    }

    public ProductInfo create(ProductCommand productCommand) {
        UUID operator = productCommand != null ? productCommand.operatorId() : UUID.randomUUID();
        Product product = Product.create(
                productCommand.name(),
                productCommand.description(),
                productCommand.price(),
                productCommand.stock(),
                productCommand.status(),
                operator
        );

        Product saved = productRepository.save(product);
        return ProductInfo.from(saved);
    }

    public ProductInfo update(UUID productId, ProductCommand productCommand) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        UUID operator = productCommand.operatorId() != null ? productCommand.operatorId() : product.getModifyId();
        product.update(productCommand.name(),
                productCommand.description(),
                productCommand.price(),
                productCommand.stock(),
                productCommand.status(),
                operator);

        Product saved = productRepository.save(product);
        return ProductInfo.from(saved);
    }

    public void delete(UUID productId) {
        productRepository.deleteById(productId);
    }
}
