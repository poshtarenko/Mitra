package com.mitra.db.dao;

import com.mitra.entity.Speciality;

import java.sql.Connection;
import java.util.List;

public interface SpecialityDao extends Dao<Integer, Speciality> {

    List<Speciality> getProfileSpecialities(Connection connection, int profileId);

    void setProfileSpecialities(Connection connection, int profileId, List<Speciality> specialities);

    void deleteAllProfileSpecialities(Connection connection, int profileId);
}
