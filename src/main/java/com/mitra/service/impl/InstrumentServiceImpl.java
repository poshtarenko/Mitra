package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.InstrumentDao;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Instrument;
import com.mitra.service.InstrumentService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentDao instrumentDao;
    private final DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;

    public InstrumentServiceImpl(InstrumentDao instrumentDao, DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper) {
        this.instrumentDao = instrumentDao;
        this.instrumentDtoMapper = instrumentDtoMapper;
    }

    @Override
    public List<InstrumentDto> getAll() {
        try (Connection connection = ConnectionManager.get()) {
            return instrumentDao.findAll(connection).stream()
                    .map(instrumentDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public List<InstrumentDto> getProfileInstruments(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return instrumentDao.getProfileInstruments(connection, profileId).stream()
                    .map(instrumentDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public void setInstrumentsToProfile(int profileId, List<InstrumentDto> instruments) {
        try (Connection connection = ConnectionManager.get()) {
            List<Instrument> instrumentsToSave = instruments.stream()
                    .map(instrumentDtoMapper::mapToEntity)
                    .collect(Collectors.toList());
            instrumentDao.setProfileInstruments(connection, profileId, instrumentsToSave);
        } catch (SQLException e) {
            // TODO : log
        }
    }
}
