package ru.clevertec.zabalotcki.service.interfce;

import java.sql.SQLException;
import java.util.List;

public interface Service<T, E> {
    List<T> getAll(int limit, int offset);

    T findById(Long id) throws SQLException;

    long add(E entity) throws SQLException;

    long update(E entity) throws SQLException;

    void deleteById(Long id);
}
