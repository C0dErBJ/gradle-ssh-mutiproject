package com.ZP.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbAccountCache {

    private static Map<Long, DbContext> cache = new HashMap<Long, DbContext>();
    private static Log log = LogFactory.getLog(DbAccountCache.class);

    static {
        DbAccountCache.init();
    }

    public static DbContext getDbContext(Long cid) {
        DbContext acc = cache.get(cid);
        return acc;
    }

    public static DbContext add(DbContext acc) {
        return cache.put(acc.getId(), acc);
    }

    public static void init() {
        try {
            cache.clear();

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DbConfig.SYS_DB_URL, DbConfig.SYS_DB_USER, DbConfig.SYS_DB_PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM company";
            ResultSet rs = stat.executeQuery(sql);

            DbContextInfo db = null;
            while (rs.next()) {
                db = new DbContextInfo();
                db.setId(rs.getLong("id"));
                db.setSchema(rs.getString("schema"));
                db.setDbaccount(rs.getString("dbaccount"));
                db.setDbpassword(rs.getString("dbpassword"));
                cache.put(db.getId(), db);
                log.info("init company dbcontext [" + db.getId() + ":" + db.getDbaccount() + "]");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void clear() {
        cache.clear();
    }

}
