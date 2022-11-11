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

    private final Drive cloudService;

    public CloudStorageProviderImpl(Drive cloudService) {
        this.cloudService = cloudService;
    }

    @Override
    @SneakyThrows
    public InputStream getImage(String photoPath) {
        // "1Hn7Sv0zuzQQAFhKQlzwpssL8Tr6xLf_-"
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        cloudService.files().get(photoPath)
                .executeMediaAndDownloadTo(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            cloudService.files().delete(filePath).execute();
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
            // 1zfNoA5EEywgbN0so_li5xiyGjl05zd2R
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
