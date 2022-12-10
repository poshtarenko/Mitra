package com.mitra.service;

import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.ProfileDto;
import com.mitra.exception.DaoException;
import com.mitra.exception.ValidationException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ProfileService {

    /**
     * Create new profile
     *
     * @param profile profileDto
     * @return id of created profile
     */
    int create(ProfileDto profile) throws ValidationException;

    /**
     * Get all user profiles from DB
     *
     * @param profileFilter filter (name, minAge, maxAge etc)
     * @param limit         max count of profiles
     * @param offset        offset
     * @return list of profileDTOs
     */
    List<ProfileDto> getAll(ProfileFilter profileFilter, int limit, int offset);

    /**
     * Get count of profiles matching filter
     *
     * @param profileFilter Filter
     * @return count of profiles matching filter
     * @throws DaoException if something bad
     */
    int getCount(ProfileFilter profileFilter) throws DaoException;

    /**
     * Get all user IDs from DB
     *
     * @return list of IDs
     */
    List<Integer> getAllIDs();

    /**
     * Find profile by ID
     *
     * @param id id of entity
     * @return profile in DB, if ID is relevant
     */
    Optional<ProfileDto> find(int id);

    /**
     * Update profile in DB with values from Profile Dto
     * Update profile photo, if newPhoto param is not null
     *
     * @param userId     id of entity
     * @param profileDto profileDTO
     * @param newPhoto   InputStream with photo
     */
    void updateProfile(int userId, ProfileDto profileDto, InputStream newPhoto) throws ValidationException;

}
