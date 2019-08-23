package org.citrix.bean;

import lombok.Data;

/**
 * @author kavin
 * @date 2019-07-18 09:56
 */
public class RemoteConnect {

    private String ip;

    private String userName;

    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}
