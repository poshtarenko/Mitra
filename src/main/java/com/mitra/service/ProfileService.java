package com.mitra.service;

import com.mitra.dto.ProfileDto;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    // TODO : comments

    List<ProfileDto> findAll();

    List<Integer> getAllIds();

    Optional<ProfileDto> find(int id);

    void updateProfile(int userId, ProfileDto profileDto);

}
