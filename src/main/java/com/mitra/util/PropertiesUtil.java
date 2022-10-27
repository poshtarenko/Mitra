package com.mitra.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        defineProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String propertyName) {
        return PROPERTIES.getProperty(propertyName);
    }

    private static void defineProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
