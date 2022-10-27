package com.mitra.db.connection;

import com.mitra.util.PropertiesUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String URL = "db.url";
    private static final String DRIVER = "db.driver";
    private static final String POOL_SIZE = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 10;

    private static BlockingQueue<Connection> pool;
    private static BlockingQueue<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }


    private ConnectionManager() {
        initConnectionPool();
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL),
                    PropertiesUtil.get(USERNAME),
                    PropertiesUtil.get(PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.get(POOL_SIZE);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = open();

            Connection proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                            ((proxy, method, args) -> method.getName().equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args)));

            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        for (Connection sourceConnection : sourceConnections) {
            try {
                sourceConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
