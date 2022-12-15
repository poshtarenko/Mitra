package com.mitra.controller.request_processor.impl;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.controller.request_processor.AbstractRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class AudioProcessor extends AbstractRequestProcessor {

    private final CloudStorageProvider cloudStorageProvider;

    public AudioProcessor(CloudStorageProvider cloudStorageProvider) {
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String audioPath = req.getParameter("path");
        InputStream image = cloudStorageProvider.getFile(audioPath);

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"song.mp3\"");
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
