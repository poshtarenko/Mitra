package com.mitra.service;

import com.mitra.dto.ProfileDto;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    // TODO : comments

    List<ProfileDto> findAll();

    Optional<ProfileDto> getByUserId(int userId);

    boolean updateProfile(int userId, ProfileDto profileDto);

}
