package com.ZP.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * ProjectName: QJZ
 * PackageName: com.ZP.util
 * User: C0dEr
 * Date: 2016-11-29
 * Time: 13:28
 * Description:
 */
public class DbManager {
    private static final String url = "jdbc:sqlserver://139.224.186.106:1433;DatabaseName=AIS20161207215649";
    private static final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String username = "kd";
    private static final String password = "kingdee@123";
    public JdbcTemplate jdbcTemplate;
    private static DbManager dbManager = null;

    private DbManager() {

    }

    public static DbManager Instance() {
        if (dbManager == null) {
            dbManager = new DbManager();
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dbManager.jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return dbManager;
    }


}
