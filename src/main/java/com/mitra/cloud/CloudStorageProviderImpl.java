package com.mitra.cloud;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class CloudStorageProviderImpl implements CloudStorageProvider {

    private static final String PROFILE_PHOTOS_FOLDER_ID = "1LFvuo2nIQibFxSV9n2JqmeKPmdjJNc6W";
    private static final String PROFILE_MUSIC_FOLDER_ID = "1vtbG-MyTsyCTbNmplowBZD7Driswt9Ff";

    private final Drive cloudService;

    public CloudStorageProviderImpl(Drive cloudService) {
        this.cloudService = cloudService;
    }

    @Override
    @SneakyThrows
    public InputStream getFile(String fileID) {
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
            File file = cloudService.files()
                    .create(fileMetadata, new InputStreamContent("image/jpeg", photoInputStream))
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addProfileMusic(int profileId, InputStream trackInputStream) {
        File fileMetadata = new File();
        fileMetadata.setName(String.valueOf(profileId));
        fileMetadata.setParents(Collections.singletonList(PROFILE_MUSIC_FOLDER_ID));

        try {
            File file = cloudService.files()
                    .create(fileMetadata, new InputStreamContent("audio/mpeg", trackInputStream))
                    .setFields("id")
                    .execute();
            return file.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
