package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.SpecialityDao;
import com.mitra.dto.SpecialityDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.impl.SpecialityImpl;
import com.mitra.service.SpecialityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityDao specialityDao;
    private final DtoMapper<SpecialityDto, SpecialityImpl> specialityDtoMapper;

    public SpecialityServiceImpl(SpecialityDao specialityDao, DtoMapper<SpecialityDto, SpecialityImpl> specialityDtoMapper) {
        this.specialityDao = specialityDao;
        this.specialityDtoMapper = specialityDtoMapper;
    }

    @Override
    public List<SpecialityDto> getAll() {
        try (Connection connection = ConnectionManager.get()) {
            return specialityDao.findAll(connection).stream()
                    .map(specialityDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public List<SpecialityDto> getProfileSpecialities(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return specialityDao.getProfileSpecialities(connection, profileId).stream()
                    .map(specialityDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public void setSpecialitiesToProfile(int profileId, List<SpecialityDto> specialities) {
        try (Connection connection = ConnectionManager.get()) {
            List<SpecialityImpl> specialitiesToSave = specialities.stream()
                    .map(specialityDtoMapper::mapToEntity)
                    .collect(Collectors.toList());
            specialityDao.setProfileSpecialities(connection, profileId, specialitiesToSave);
        } catch (SQLException e) {
            // TODO : log
        }
    }
}
