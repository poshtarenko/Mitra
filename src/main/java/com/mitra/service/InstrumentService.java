package com.mitra.service;

import com.mitra.dto.InstrumentDto;

import java.util.List;

public interface InstrumentService {

    List<InstrumentDto> getAll();

    List<InstrumentDto> getProfileInstruments(int profileId);

    void setInstrumentsToProfile(int profileId, List<InstrumentDto> instruments);
}
