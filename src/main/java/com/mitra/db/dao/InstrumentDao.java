package com.mitra.db.dao;

import com.mitra.entity.impl.Instrument;

import java.sql.Connection;
import java.util.List;

public interface InstrumentDao extends Dao<Integer, Instrument> {

    /**
     * Get list of all profile instruments
     *
     * @param connection connection to db
     * @param profileId  profileId
     * @return list of instruments which belong to profile
     */
    List<Instrument> getProfileInstruments(Connection connection, int profileId);

    /**
     * Updates profile instrument list to new
     *
     * @param connection  connection to db
     * @param profileId   profileId
     * @param instruments list of instruments
     */
    void setProfileInstruments(Connection connection, int profileId, List<Instrument> instruments);

    /**
     * Deletes all profile instruments
     *
     * @param connection connection to db
     * @param profileId  profileId
     */
    void deleteAllProfileInstruments(Connection connection, int profileId);

}
