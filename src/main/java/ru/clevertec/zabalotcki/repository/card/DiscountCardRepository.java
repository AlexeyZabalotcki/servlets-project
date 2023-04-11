package ru.clevertec.zabalotcki.repository.card;

import ru.clevertec.zabalotcki.model.DiscountCard;
import ru.clevertec.zabalotcki.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiscountCardRepository implements Repository<DiscountCard> {

    private final Connection connection;

    public DiscountCardRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DiscountCard> findAll(int limit, int offset) {
        List<DiscountCard> cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM discount_card LIMIT ? OFFSET ?")) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    DiscountCard card = new DiscountCard();
                    card.setId(resultSet.getLong("id"));
                    card.setNumber(resultSet.getInt("number"));
                    card.setDiscount(resultSet.getBoolean("discount"));
                    cards.add(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cards;
    }

    @Override
    public DiscountCard findById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM discount_card WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DiscountCard card = new DiscountCard();
                card.setId(resultSet.getLong("id"));
                card.setNumber(resultSet.getInt("number"));
                card.setDiscount(resultSet.getBoolean("discount"));
                return card;
            } else {
                throw new SQLException("Unable to get card");
            }
        }
    }

    @Override
    public long update(DiscountCard entity) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE discount_card SET number = ?, discount = ? WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getNumber());
            statement.setBoolean(2, entity.isDiscount());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Unable to get generated key for existing card");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long save(DiscountCard entity) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO discount_card (number, discount) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getNumber());
            statement.setBoolean(2, entity.isDiscount());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Unable to get generated key for new card");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM discount_card WHERE id=?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
