package com.mitra.service.impl;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.dao.TrackDao;
import com.mitra.dto.TrackDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Track;
import com.mitra.service.TrackService;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TrackServiceImpl implements TrackService {

    private final DtoMapper<TrackDto, Track> trackDtoMapper;
    private final ProfileDao profileDao;
    private final TrackDao trackDao;
    private final CloudStorageProvider cloudStorageProvider;

    public TrackServiceImpl(DtoMapper<TrackDto, Track> trackDtoMapper, ProfileDao profileDao,
                            TrackDao trackDao, CloudStorageProvider cloudStorageProvider) {
        this.trackDtoMapper = trackDtoMapper;
        this.profileDao = profileDao;
        this.trackDao = trackDao;
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public List<TrackDto> getProfileMusic(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return trackDao.getProfileMusic(connection, profileId).stream()
                    .map(trackDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            log.error("Getting profile music failed");
            return Collections.emptyList();
        }
    }

    @Override
    public void setProfilePreviewTrack(int profileId, int trackId) {
        try (Connection connection = ConnectionManager.get()) {
            profileDao.setPreviewTrack(connection, profileId, trackId);
        } catch (SQLException e) {
            log.error("Setting profile preview track failed");
        }
    }

    @Override
    public void save(TrackDto track, InputStream trackInputStream) {
        try (Connection connection = ConnectionManager.get()) {
            String fileId = cloudStorageProvider.addProfileMusic(track.getOwnerId(), trackInputStream);
            Track trackToSave = trackDtoMapper.mapToEntity(track);
            trackToSave.setFilePath(fileId);
            trackDao.save(connection, trackToSave);
        } catch (SQLException e) {
            log.error("Track saving failed");
        }
    }

    @Override
    public void remove(int trackId) {
        try (Connection connection = ConnectionManager.get()) {
            trackDao.delete(connection, trackId);
        } catch (SQLException e) {
            log.error("Track removing failed");
        }
    }
}
