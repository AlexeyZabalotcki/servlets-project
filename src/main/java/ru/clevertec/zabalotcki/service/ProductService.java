package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.ProductDto;
import ru.clevertec.zabalotcki.model.Product;
import ru.clevertec.zabalotcki.repository.Repository;
import ru.clevertec.zabalotcki.repository.product.ProductRepository;
import ru.clevertec.zabalotcki.service.interfce.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class ProductService implements Service<ProductDto, Product> {

    private final Repository<Product> productRepository;

    public ProductService(Connection connection) {
        productRepository = new ProductRepository(connection);
    }

    public List<ProductDto> getAll(int limit, int offset) {
        List<Product> products = productRepository.findAll(limit, offset);
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProductDto findById(Long id) throws SQLException {
        Product product = productRepository.findById(id);
        return toDto(product);
    }

    public long add(Product entity) throws SQLException {
        return productRepository.save(entity);
    }

    public long update(Product entity) throws SQLException {
        return productRepository.update(entity);
    }

    public void deleteById(Long id) {
        productRepository.delete(id);
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .discount(product.isDiscount())
                .build();
    }
}
