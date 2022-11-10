package com.mitra.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/* class to demonstarte use of Drive files list API */
public class GoogleDriveProvider {

    private static final String APPLICATION_NAME = "Mitra";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<>(DriveScopes.all());
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

//    static {
//        SCOPES.add(DriveScopes.DRIVE_FILE);
//        SCOPES.add("https://www.googleapis.com/auth/drive");
//        SCOPES.add("https://www.googleapis.com/auth/drive.file");
//        SCOPES.add("https://www.googleapis.com/auth/drive.appdata");
//        SCOPES.add("https://www.googleapis.com/auth/drive.scripts");
//        SCOPES.add("https://www.googleapis.com/auth/drive.metadata");
//    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveProvider.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client driveService.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return driveService;
    }

    public static void saveImgTest(String path) throws GeneralSecurityException, IOException {
        Drive driveService = getDriveService();

        InputStream result = driveService.files().get("1Hn7Sv0zuzQQAFhKQlzwpssL8Tr6xLf_-")
                .executeMediaAsInputStream();

        System.out.println(result);

//        File fileMetadata = new File();
//        fileMetadata.setName("photo1.jpg");
//        // File's content.
//
//        java.io.File filePath = new java.io.File(path + "/resources/img/profile/139.jpg");
//        // Specify media type and file-path for file.
//        FileContent mediaContent = new FileContent("image/jpeg", filePath);
//        try {
//            File file = driveService.files().create(fileMetadata, mediaContent)
//                    .setFields("id")
//                    .execute();
//            System.out.println("File ID: " + file.getId());
//            Permission newPermission = new Permission();
//            newPermission.setType("anyone");
//            newPermission.setRole("reader");
//            driveService.permissions().create(file.getId(), newPermission);
//            // 1zfNoA5EEywgbN0so_li5xiyGjl05zd2R
//        } catch (GoogleJsonResponseException e) {
//            System.err.println("Unable to upload file: " + e.getDetails());
//            throw e;
//        }
    }

//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        saveImgTest("C:\\Users\\catem\\IdeaProjects\\Mitra\\src\\main\\webapp");
//    }

}