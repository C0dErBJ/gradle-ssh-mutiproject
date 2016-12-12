package com.CD.db;

/**
 * 用户上下文环境
 *
 * @author HuangJian
 */
public interface DbContext {

    public Long getId();

    //用户数据库
    public String getSchema();

    //数据库访问帐号
    public String getDbaccount();

    //数据库密码
    public String getDbpassword();
}
