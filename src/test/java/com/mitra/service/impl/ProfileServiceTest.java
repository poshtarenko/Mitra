package com.mitra.service.impl;

import com.mitra.dto.ProfileDto;
import com.mitra.entity.Gender;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileServiceTest {

    static ProfileService profileService;

    static ProfileDto profile;
    static ProfileDto profileCopy;
    static int id = 141;

    @BeforeAll
    static void setUp() {
        profileService = ServiceFactory.getInstance().getProfileService();
    }

    @AfterAll
    static void tearDown() {
        profileService.updateProfile(141, profileCopy);
    }

    @Test
    @Order(1)
    void find() {
        Optional<ProfileDto> profileOptional = profileService.find(id);

        if (!profileOptional.isPresent())
            fail("Профіль не знайдено");

        profile = profileOptional.get();

        profileCopy = profileService.find(id).get();
    }

    @Test
    @Order(2)
    void findAll() {
        List<ProfileDto> allProfiles = profileService.findAll();
        Optional<ProfileDto> profileOptional = allProfiles.stream()
                .filter(p -> p.getId().equals(profile.getId()))
                .findFirst();

        if (!profileOptional.isPresent())
            fail("У переліку всіх анкет немає нашої анкети");

        ProfileDto profile2 = profileOptional.get();

        assertEquals(profile.getAge(), profile2.getAge());
        assertEquals(profile.getName(), profile2.getName());
        assertEquals(profile.getPhotoPath(), profile2.getPhotoPath());
        assertEquals(profile.getText(), profile2.getText());
        assertEquals(profile.getGender(), profile2.getGender());
    }

    @Test
    @Order(3)
    void find2() {
        Optional<ProfileDto> profileOptional = profileService.find(id);

        if (!profileOptional.isPresent())
            fail("У переліку всіх анкет немає нашої анкети");

        ProfileDto profile2 = profileOptional.get();

        assertEquals(profile.getAge(), profile2.getAge());
        assertEquals(profile.getName(), profile2.getName());
        assertEquals(profile.getPhotoPath(), profile2.getPhotoPath());
        assertEquals(profile.getText(), profile2.getText());
        assertEquals(profile.getGender(), profile2.getGender());
    }


    @Test
    @Order(4)
    void getAllIds() {
        List<Integer> allIds = profileService.getAllIDs();
        List<Integer> allIds2 = profileService.findAll().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        for (Integer profileId : allIds) {
            Optional<Integer> equalId = allIds2.stream().filter(i -> i.equals(profileId)).findFirst();
            if (!equalId.isPresent()) {
                fail("Список id, отриманий від методу getAllIds, і список id, отриманий від методу findAll " +
                        " з подальшим відділенням id, не співпадають");
            }
        }
    }

    @Test
    @Order(5)
    void updateProfile() {
        ProfileDto profileToUpdate = ProfileDto.builder()
                .id(id)
                .age(18)
                .name("New Name")
                .text("Some new text...")
                .gender(Gender.MALE)
                .location(profile.getLocation())
                .photoPath(profile.getPhotoPath())
                .build();

        profileService.updateProfile(141, profileToUpdate);

        profile = profileService.find(id).get();

        assertEquals(profile.getAge(), profileToUpdate.getAge());
        assertEquals(profile.getName(), profileToUpdate.getName());
        assertEquals(profile.getPhotoPath(), profileToUpdate.getPhotoPath());
        assertEquals(profile.getText(), profileToUpdate.getText());
        assertEquals(profile.getGender(), profileToUpdate.getGender());
    }
}