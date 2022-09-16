package com.realfinance.sofa.sdebank.sftp;


public enum SftpCategory {
    //关联交易
    ASSOCIATEDTRAN("172.16.249.133", 22, "use", "use_1234"),
    //统一门户
    DATASYNC("172.16.249.83", 22, "eip", "eip123"),
    //OA
    OA("172.16.249.18", 22, "eosftp", "eosftp123");

    //主机
    private String host;
    //端口
    private Integer port;
    //用户名
    private String username;
    //密码
    private String password;

    SftpCategory() {}

    SftpCategory(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

