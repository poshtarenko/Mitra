package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.*;
import com.mitra.db.dao.impl.util.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.*;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDaoImpl implements TrackDao {

    private final RowMapper<Track> musicRowMapper;
    private final QueryExecutor<Integer, Track> queryExecutor;

    public TrackDaoImpl(RowMapper<Track> musicRowMapper) {
        this.musicRowMapper = musicRowMapper;
        this.queryExecutor = new QueryExecutor<>(musicRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s, %s FROM %s ",
            Column.MUSIC.ID, Column.MUSIC.NAME, Column.MUSIC.AUTHOR,
            Column.MUSIC.FILE_PATH, Column.MUSIC.PROFILE_ID, Table.MUSIC);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.MUSIC.ID);

    public static final String FIND_PROFILE_MUSIC_SQL = FIND_ALL_SQL +
            String.format(" WHERE %s = ?", Column.MUSIC.PROFILE_ID);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            Table.MUSIC, Column.MUSIC.NAME.shortName(), Column.MUSIC.AUTHOR.shortName(),
            Column.MUSIC.FILE_PATH.shortName(), Column.MUSIC.PROFILE_ID.shortName());

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
            Table.MUSIC, Column.MUSIC.NAME.shortName(), Column.MUSIC.AUTHOR.shortName(),
            Column.MUSIC.FILE_PATH.shortName(), Column.MUSIC.PROFILE_ID.shortName(), Column.MUSIC.ID.shortName());

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.MUSIC, Column.MUSIC.ID);

    @Override
    public Optional<Track> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.selectOne(connection, FIND_SQL, id);
    }


    @Override
    public List<Track> findAll(Connection connection) throws DaoException {
        return queryExecutor.selectMany(connection, FIND_ALL_SQL);
    }

    @Override
    public List<Track> getProfileMusic(Connection connection, int profileId) {
        return queryExecutor.selectMany(connection, FIND_PROFILE_MUSIC_SQL, profileId);
    }

    @Override
    public Integer save(Connection connection, Track entity) throws DaoException {
        return queryExecutor.insert(connection, SAVE_SQL,
                entity.getName(), entity.getAuthor(), entity.getFilePath(), entity.getOwner().getId());
    }

    @Override
    public void update(Connection connection, Integer id, Track entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getName(), entity.getAuthor(), entity.getFilePath(), entity.getOwner().getId(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }

}