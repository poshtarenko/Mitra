package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.LocationDao;
import com.mitra.db.dao.impl.LocationDaoImpl;
import com.mitra.entity.Location;
import com.mitra.service.LocationService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LocationServiceImpl implements LocationService {

    private static final LocationDao locationDao = LocationDaoImpl.getInstance();

    @Override
    public List<String> getAllCities() {
        try (Connection connection = ConnectionManager.get()) {
            List<String> cities = new ArrayList<>();

            List<Location> locations = locationDao.findAll(connection);
            for (Location location : locations) {
                cities.add(location.getCity());
            }
            return cities;
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Location> getById(int locationId) {
        try (Connection connection = ConnectionManager.get()) {
            return getById(connection, locationId);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public Optional<Location> getById(Connection connection, int locationId) {
        return locationDao.find(connection, locationId);
    }

    @Override
    public Optional<Location> getByCity(String city) {
        try (Connection connection = ConnectionManager.get()) {
            return getByCity(connection, city);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public Optional<Location> getByCity(Connection connection, String city) {
        return locationDao.findByCity(connection, city);
    }
}
