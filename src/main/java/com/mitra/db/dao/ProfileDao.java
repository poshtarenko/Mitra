package com.mitra.db.dao;

import com.mitra.db.filter.ProfileFilter;
import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface ProfileDao extends Dao<Integer, Profile> {

    /**
     * Execute select of part of db entity table with filter
     *
     * @param connection    Connection to db
     * @param profileFilter Filter (name, minAge, maxAge etc)
     * @param limit         max count of rows
     * @param offset        offset
     * @return list of found mapped entities
     * @throws DaoException if something bad
     */
    List<Profile> findAll(Connection connection, ProfileFilter profileFilter, int limit, int offset) throws DaoException;

    /**
     * Get count of rows matching filter
     *
     * @param connection    Connection to db
     * @param profileFilter Filter (name, minAge, maxAge etc)
     * @return count of rows matching filter
     * @throws DaoException if something bad
     */
    int getCount(Connection connection, ProfileFilter profileFilter) throws DaoException;

    /**
     * Get list of all profile IDs
     *
     * @param connection connection to db
     * @return list of all profile IDs
     */
    List<Integer> getIdsForSwipeSearch(Connection connection) throws DaoException;

    /**
     * Get list of all profile IDs
     *
     * @param connection connection to db
     * @param profileId  profile id
     * @param trackId    track id
     */
    void setPreviewTrack(Connection connection, int profileId, int trackId) throws DaoException;
}
