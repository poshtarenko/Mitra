package com.mitra.db.dao;

import com.mitra.entity.Instrument;

import java.sql.Connection;
import java.util.List;

public interface InstrumentDao extends Dao<Integer, Instrument> {

    List<Instrument> getProfileInstruments(Connection connection, int profileId);

    void setProfileInstruments(Connection connection, int profileId, List<Instrument> instruments);

    void deleteAllProfileInstruments(Connection connection, int profileId);

}
