package com.mitra.controller.impl.get;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.controller.impl.GetController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ImageController implements GetController {

    private final CloudStorageProvider cloudStorageProvider;

    public ImageController(CloudStorageProvider cloudStorageProvider) {
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imagePath = req.getParameter("path");
        InputStream image = cloudStorageProvider.getFile(imagePath);

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
