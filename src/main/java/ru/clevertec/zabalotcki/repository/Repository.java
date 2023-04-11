package ru.clevertec.zabalotcki.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T> findAll(int limit, int offset);

    T findById(Long id) throws SQLException;

    long update(T entity) throws SQLException;

    long save(T entity) throws SQLException;

    void delete(Long id);
}
