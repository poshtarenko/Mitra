package com.mitra.db.dao;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Identifiable;
import com.mitra.exception.DaoException;

import java.sql.*;
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
                return Optional.of(rowMapper.map(resultSet));

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
                list.add(rowMapper.map(resultSet));

            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public K save(Connection connection, String sql, Object... params) {
        try (PreparedStatement preparedStatement = fillPreparedStatement(connection, sql, Statement.RETURN_GENERATED_KEYS, params)) {
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
        return fillPreparedStatement(connection, sql, Statement.NO_GENERATED_KEYS, params);
    }

    private PreparedStatement fillPreparedStatement(Connection connection, String sql, int autoGeneratedKeys, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, autoGeneratedKeys);

            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof List) {
                    for (Object element : (List) params[i]) {
                        preparedStatement.setObject(i + 1, element);
                        i++;
                    }
                } else {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }

            System.out.println(preparedStatement);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
