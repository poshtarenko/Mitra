package com.mitra.controller.impl.get;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.controller.impl.GetController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class AudioController implements GetController {

    private final CloudStorageProvider cloudStorageProvider;

    public AudioController(CloudStorageProvider cloudStorageProvider) {
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String audioPath = req.getParameter("path");
        InputStream image = cloudStorageProvider.getFile(audioPath);

        resp.setContentType("application/octet-stream");
        outputAudio(image, resp);
        image.close();
    }

    private void outputAudio(InputStream imageInputStream, HttpServletResponse resp) {
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            byte[] audio = new byte[imageInputStream.available()];
            imageInputStream.read(audio);
            outputStream.write(audio);
        } catch (IOException e) {
            resp.setStatus(404);
        }
    }
}
