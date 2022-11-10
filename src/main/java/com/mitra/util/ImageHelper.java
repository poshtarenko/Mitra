package com.mitra.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class ImageHelper {

    public static void save(String imagePath, InputStream imageContent) throws IOException {
        Path path = Paths.get(imagePath);

        byte[] image = new byte[imageContent.available()];
        imageContent.read(image);

        Files.createDirectories(path.getParent());
        Files.write(path, image, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
