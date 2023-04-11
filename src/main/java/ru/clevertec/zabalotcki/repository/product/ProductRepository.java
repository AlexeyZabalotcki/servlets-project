package ru.clevertec.zabalotcki.repository.product;

import ru.clevertec.zabalotcki.model.Product;
import ru.clevertec.zabalotcki.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product> {

    private final Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll(int limit, int offset) {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM products LIMIT ? OFFSET ?")) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setTitle(resultSet.getString("title"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    product.setDiscount(resultSet.getBoolean("discount"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Product findById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setTitle(resultSet.getString("title"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDiscount(resultSet.getBoolean("discount"));
                return product;
            } else {
                throw new SQLException("Unable to get product");
            }
        }
    }

    @Override
    public long update(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE products SET title = ?, price = ?, discount = ? WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setBoolean(3, entity.isDiscount());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Unable to get generated key for existing product");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long save(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO products (title, price, discount) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setBoolean(3, entity.isDiscount());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Unable to get generated key for new product");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id=?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
