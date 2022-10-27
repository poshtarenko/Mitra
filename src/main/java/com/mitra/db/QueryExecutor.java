package com.mitra.db;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Identifiable;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryExecutor<K extends Number, E extends Identifiable<K>> {

    private final RowMapper<E> rowMapper;

    public QueryExecutor(RowMapper<E> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public Optional<E> find(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = fillPreparedStatement(connection, sql, params)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return Optional.of(rowMapper.map(connection, resultSet));

            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<E> findAll(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = fillPreparedStatement(connection, sql, params)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<E> list = new ArrayList<>();

            while (resultSet.next())
                list.add(rowMapper.map(connection, resultSet));

            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public K save(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = fillPreparedStatement(connection, sql, params)) {
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return (K) generatedKeys.getObject(1);
            }

            throw new SQLException();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = fillPreparedStatement(connection, sql, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PreparedStatement fillPreparedStatement(Connection connection, String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
