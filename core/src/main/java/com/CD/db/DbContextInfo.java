package com.CD.db;

public class DbContextInfo implements DbContext {

    private Long id;

    private String schema;
    private String dbaccount;
    private String dbpassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDbaccount() {
        return dbaccount;
    }

    public void setDbaccount(String dbaccount) {
        this.dbaccount = dbaccount;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

}
