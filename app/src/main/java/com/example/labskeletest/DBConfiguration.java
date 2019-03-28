package com.example.labskeletest;

public class DBConfiguration {

    //data source=EC2AMAZ-1MDV8EJ;initial catalog=lablocator;user id=lablocatorspring2019

    private String serverName = "lablocator2019.ctlracv9acbx.us-east-2.rds.amazonaws.com:1433";
    private String userName = "lablocatorspring2019";
    private String password = "LabLocatorSpring2019";
    private String driver = "net.sourceforge.jtbs.jdbc.Driver";
    private String db = "lablocator";
    private String connectionStr = "jdbc:jtds:sqlserver://" + serverName + ";database=" + db + ";user=" + userName + ";password=" + password + ";";

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getConnectionStr() {
        return connectionStr;
    }

    public void setConnectionStr(String connectionStr) {
        this.connectionStr = connectionStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

}
