package com.mitra.db.dao.impl.util;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.entity.Gender;
import com.mitra.entity.Instrument;
import com.mitra.entity.Speciality;

import java.util.List;

public final class ProfileDaoQueryConstructor {

    private static final String baseFindSQL = String.format(
            "SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "LEFT JOIN %s ON %s = %s ",
            Column.PROFILE.ID, Column.PROFILE.NAME, Column.PROFILE.AGE, Column.GENDER.GENDER, Column.PROFILE.TEXT, Column.PROFILE.PHOTO_PATH,
            Column.CITY.ID, Column.CITY.NAME, Column.LOCAL_AREA.NAME, Column.REGION.NAME, Column.COUNTRY.NAME,
            Column.MUSIC.ID, Column.MUSIC.NAME, Column.MUSIC.AUTHOR, Column.MUSIC.FILE_PATH,
            Table.PROFILE,
            Table.GENDER, Column.PROFILE.GENDER_ID, Column.GENDER.ID,
            Table.CITY, Column.PROFILE.CITY_ID, Column.CITY.ID,
            Table.LOCAL_AREA, Column.CITY.LOCAL_AREA_ID, Column.LOCAL_AREA.ID,
            Table.REGION, Column.LOCAL_AREA.REGION_ID, Column.REGION.ID,
            Table.COUNTRY, Column.REGION.COUNTRY_ID, Column.COUNTRY.ID,
            Table.MUSIC, Column.MUSIC.ID, Column.PROFILE.PREVIEW_MUSIC_ID);

    private static final String baseCountSQL = String.format(
            "SELECT COUNT(%s) FROM %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "LEFT JOIN %s ON %s = %s ",
            Column.PROFILE.ID, Table.PROFILE,
            Table.GENDER, Column.PROFILE.GENDER_ID, Column.GENDER.ID,
            Table.CITY, Column.PROFILE.CITY_ID, Column.CITY.ID,
            Table.LOCAL_AREA, Column.CITY.LOCAL_AREA_ID, Column.LOCAL_AREA.ID,
            Table.REGION, Column.LOCAL_AREA.REGION_ID, Column.REGION.ID,
            Table.COUNTRY, Column.REGION.COUNTRY_ID, Column.COUNTRY.ID,
            Table.MUSIC, Column.MUSIC.ID, Column.PROFILE.PREVIEW_MUSIC_ID);

    public static StringBuilder constructSelectQuery(ProfileFilter profileFilter) {
        return constructQuery(profileFilter, baseFindSQL);
    }

    public static StringBuilder constructCountQuery(ProfileFilter profileFilter) {
        return constructQuery(profileFilter, baseCountSQL);
    }

    private static StringBuilder constructQuery(ProfileFilter profileFilter, String initialSQL) {
        StringBuilder SQL = new StringBuilder(initialSQL);
        boolean whereClauseProvided = false;

        String name = profileFilter.getName();
        if (name != null) {
            SQL.append(String.format("WHERE %s LIKE '%s' ", Column.PROFILE.NAME, name));
            whereClauseProvided = true;
        }

        Gender gender = profileFilter.getGender();
        if (gender != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s = '%s' ", Column.GENDER.GENDER, gender));
        }

        Integer minAge = profileFilter.getMinAge();
        if (minAge != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s >= %d ", Column.PROFILE.AGE, minAge));
        }

        Integer maxAge = profileFilter.getMaxAge();
        if (maxAge != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s <= %d ", Column.PROFILE.AGE, maxAge));
        }

        String city = profileFilter.getCity();
        if (city != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s = '%s' ", Column.CITY.NAME, city));
        }

        String localArea = profileFilter.getLocalArea();
        if (localArea != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s = '%s' ", Column.LOCAL_AREA.NAME, localArea));
        }

        String region = profileFilter.getRegion();
        if (region != null) {
            whereClauseProvided = appendClause(whereClauseProvided, SQL);
            SQL.append(String.format("%s = '%s' ", Column.REGION.NAME, region));
        }

        List<Instrument> instrumentsList = profileFilter.getInstruments();
        if (instrumentsList != null) {
            for (Instrument instrument : instrumentsList) {
                whereClauseProvided = appendClause(whereClauseProvided, SQL);
                SQL.append(String.format("%s IN (SELECT %s FROM %s JOIN %s ON %s = %s WHERE %s = '%s') ",
                        Column.PROFILE.ID, Column.PROFILE_INSTRUMENT.PROFILE_ID, Table.PROFILE_INSTRUMENT,
                        Table.INSTRUMENT, Column.INSTRUMENT.ID, Column.PROFILE_INSTRUMENT.INSTRUMENT_ID,
                        Column.INSTRUMENT.NAME, instrument.getName()));
            }
        }

        List<Speciality> specialityList = profileFilter.getSpecialities();
        if (specialityList != null) {
            for (Speciality speciality : specialityList) {
                whereClauseProvided = appendClause(whereClauseProvided, SQL);
                SQL.append(String.format("%s IN (SELECT %s FROM %s JOIN %s ON %s = %s WHERE %s = '%s') ",
                        Column.PROFILE.ID, Column.PROFILE_SPECIALITY.PROFILE_ID, Table.PROFILE_SPECIALITY,
                        Table.SPECIALITY, Column.SPECIALITY.ID, Column.PROFILE_SPECIALITY.SPECIALITY_ID,
                        Column.SPECIALITY.NAME, speciality.getName()));
            }
        }

        return SQL;
    }

    private static boolean appendClause(boolean whereClauseProvided, StringBuilder SQL) {
        if (whereClauseProvided) {
            SQL.append("AND ");
        }
        else {
            SQL.append("WHERE ");
        }
        return true;
    }
}
