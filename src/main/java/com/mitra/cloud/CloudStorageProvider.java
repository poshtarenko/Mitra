package com.mitra.cloud;

import java.io.InputStream;

public interface CloudStorageProvider {

    /**
     * Get image from cloud
     *
     * @param fileID fileID of image
     * @return input stream with image
     */
    InputStream getImage(String fileID);

    /**
     * Deletes any file from cloud by ID
     *
     * @param fileID fileID of image
     */
    void deleteFile(String fileID);

    /**
     * Get image from cloud
     *
     * @param profileId        profile id
     * @param photoInputStream input stream with image
     * @return generated fileId
     */
    String setProfilePhoto(int profileId, InputStream photoInputStream);


    /**
     * Get image from cloud
     *
     * @param profileId        profile id
     * @param musicInputStream input stream with track
     * @return generated fileId
     */
    String addProfileMusic(int profileId, InputStream musicInputStream);

}
