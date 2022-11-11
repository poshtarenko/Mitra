package com.mitra.controller.request_processor.impl;

import com.mitra.cloud.CloudStorageProvider;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ImageProcessor extends AbstractRequestProcessor {

    private final CloudStorageProvider cloudStorageProvider;

    public ImageProcessor(CloudStorageProvider cloudStorageProvider) {
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imagePath = req.getParameter("path");
        InputStream image = cloudStorageProvider.getImage(imagePath);

        resp.setContentType("application/octet-stream");
        writeImage(image, resp);
        image.close();
    }

    private void writeImage(InputStream imageInputStream, HttpServletResponse resp) {
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            byte[] image = new byte[imageInputStream.available()];
            imageInputStream.read(image);
            outputStream.write(image);
        } catch (IOException e) {
            resp.setStatus(404);
        }
    }
}
