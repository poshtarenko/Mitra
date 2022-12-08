package com.mitra.service;

import com.mitra.dto.InstrumentDto;

import java.util.List;

public interface InstrumentService {

    /**
     * Get all instruments
     *
     * @return list of instruments
     */
    List<InstrumentDto> getAll();

    /**
     * Get all profile instruments
     *
     * @param profileId profile id
     * @return list of profile instruments
     */
    List<InstrumentDto> getProfileInstruments(int profileId);

    /**
     * Set instruments list to profile
     *
     * @param profileId profile id
     * @param instruments instruments list
     */
    void setInstrumentsToProfile(int profileId, List<InstrumentDto> instruments);
}
