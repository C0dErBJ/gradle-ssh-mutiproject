/**
 *
 */
package com.ZP.db;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author HuangJian
 * @date 2013-8-1
 */
public class DbConfig {

    public static String deployPath = "";

    public static int pageSize = 12;

    public static String SYS_DB_IP = "";

    public static String SYS_DB_URL = "";

    public static String SYS_DB_USER = "";

    public static String SYS_DB_PASSWORD = "";

    public static void init() {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("utf8");
        try {
            config.load("fmprc.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        SYS_DB_IP = config.getString("sys_db_ip");
        SYS_DB_URL = config.getString("sys_db_url");
        SYS_DB_USER = config.getString("sys_db_user");
        SYS_DB_PASSWORD = config.getString("sys_db_password");
    }
}

