package com.mitra.cloud;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.mitra.dto.ProfileDto;
import lombok.SneakyThrows;

import java.io.*;
import java.util.Collections;

public class CloudStorageProviderImpl implements CloudStorageProvider {

    private static final String PROFILE_PHOTOS_FOLDER_ID = "1LFvuo2nIQibFxSV9n2JqmeKPmdjJNc6W";
    private static final String PROFILE_MUSIC_FOLDER_ID = "EMPTY NOW";

    private final Drive cloudService;

    public CloudStorageProviderImpl(Drive cloudService) {
        this.cloudService = cloudService;
    }

    @Override
    @SneakyThrows
    public InputStream getImage(String fileID) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        cloudService.files().get(fileID)
                .executeMediaAndDownloadTo(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void deleteFile(String fileID) {
        try {
            cloudService.files().delete(fileID).execute();
        } catch (IOException e) {
            // TODO : log warning
        }
    }

    @Override
    public String setProfilePhoto(int profileId, InputStream photoInputStream) {
        File fileMetadata = new File();
        fileMetadata.setName(profileId + ".jpg");
        fileMetadata.setParents(Collections.singletonList(PROFILE_PHOTOS_FOLDER_ID));

        try {
            File file = cloudService.files().create(fileMetadata, new InputStreamContent("image/jpeg", photoInputStream))
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addProfileMusic(int profileId, InputStream musicInputStream) {
        File fileMetadata = new File();
        fileMetadata.setName(profileId + ".mp3");
        fileMetadata.setParents(Collections.singletonList(PROFILE_MUSIC_FOLDER_ID));

        try {
            File file = cloudService.files().create(fileMetadata, new InputStreamContent("MUSICc MUSTt BEc HERREE", musicInputStream))
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
