package com.mitra.db.mapper;

import com.mitra.entity.Like;
import com.mitra.entity.Reaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like map(ResultSet resultSet) throws SQLException {
        return new Like(
                0,
                resultSet.getInt(1),
                resultSet.getInt(2),
                Reaction.getReactionByCode(resultSet.getInt(3))
        );
    }
}
