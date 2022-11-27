package com.mitra.db.dao;

import com.mitra.entity.impl.SpecialityImpl;

import java.sql.Connection;
import java.util.List;

public interface SpecialityDao extends Dao<Integer, SpecialityImpl> {

    /**
     * Get list of all profile specialities
     *
     * @param connection connection to db
     * @param profileId  profileId
     * @return list of specialities which profile have
     */
    List<SpecialityImpl> getProfileSpecialities(Connection connection, int profileId);

    /**
     * Updates profile specialities list to new
     *
     * @param connection  connection to db
     * @param profileId   profileId
     * @param specialities list of specialities
     */
    void setProfileSpecialities(Connection connection, int profileId, List<SpecialityImpl> specialities);

    /**
     * Deletes all profile specialities
     *
     * @param connection connection to db
     * @param profileId  profileId
     */
    void deleteAllProfileSpecialities(Connection connection, int profileId);
}
