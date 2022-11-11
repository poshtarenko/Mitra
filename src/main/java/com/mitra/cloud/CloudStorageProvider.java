package com.mitra.cloud;

import java.io.InputStream;

public interface CloudStorageProvider {

    InputStream getImage(String photoPath);

    void deleteFile(String filePath);

    // returns generated fileId (=photoPath)
    String setProfilePhoto(int profileId, InputStream photoInputStream);

}
