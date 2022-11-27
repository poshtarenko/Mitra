package com.mitra.db.mapper;

import com.mitra.entity.Location;
import com.mitra.entity.impl.LocationImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LocationRowMapperTest {

    RowMapper<Location> locationRowMapper;

    static String city = "Odesa";
    static String localArea = "Prymorsky rayon";
    static String region = "Odeska oblast";
    static String country = "Ukraine";

    @Test
    void map() {
        locationRowMapper = RowMapperFactory.getInstance().getLocationRowMapper();

        Location location = null;
        try (ResultSet rs = Mockito.mock(ResultSet.class)) {
            Mockito.when(rs.getString(1)).thenReturn(city);
            Mockito.when(rs.getString(2)).thenReturn(localArea);
            Mockito.when(rs.getString(3)).thenReturn(region);
            Mockito.when(rs.getString(4)).thenReturn(country);

            location = locationRowMapper.map(rs);
        } catch (SQLException e) {
            fail(e);
        }

        assertEquals(location.getCity(), city);
        assertEquals(location.getLocalArea(), localArea);
        assertEquals(location.getRegion(), region);
        assertEquals(location.getCountry(), country);
    }
}