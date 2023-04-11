package ru.clevertec.zabalotcki.repository.receipt;

import ru.clevertec.zabalotcki.model.Receipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiptRepositoryImpl implements ReceiptRepository {

    private final Connection connection;

    public ReceiptRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Receipt save(Receipt receipt) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO receipt (receipt) VALUES (?) RETURNING id")) {
            statement.setBytes(1, receipt.getReceipt());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                receipt.setId(id);
                return receipt;
            } else {
                throw new SQLException("Unable to save receipt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT receipt FROM receipt WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                byte[] receiptData = rs.getBytes("receipt");
                return receiptData;
            } else {
                throw new SQLException("Unable to get receipt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
