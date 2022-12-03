package com.panchuk.taxapp.dao.mysql.util;

import com.panchuk.taxapp.constant.ProjectConstant;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPDataSource {
    private static final BasicDataSource ds = new BasicDataSource();
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;

    static {
        try {
            properties.load(new FileReader(ProjectConstant.SETTINGS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DATABASE_URL = (String) properties.get(ProjectConstant.CONNECTION_URL);
    }

    static {
        ds.setUrl(DATABASE_URL);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    private DBCPDataSource(){ }
}
