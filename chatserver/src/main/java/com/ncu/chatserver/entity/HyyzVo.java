package com.ncu.chatserver.entity;

public class HyyzVo {

    private Integer id;

    private Integer userId;

    private String userName;

    private String userAvatar;

    private String nr;

    private String zt;

    private String role;

    private Integer hyUserId;

    private String hyGroupName;

    public Integer getHyUserId() {
        return hyUserId;
    }

    public void setHyUserId(Integer hyUserId) {
        this.hyUserId = hyUserId;
    }

    public String getHyGroupName() {
        return hyGroupName;
    }

    public void setHyGroupName(String hyGroupName) {
        this.hyGroupName = hyGroupName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    @Override
    public String toString() {
        return "HyyzVo{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", nr='" + nr + '\'' +
                ", zt='" + zt + '\'' +
                ", role='" + role + '\'' +
                ", hyGroupName='" + hyGroupName + '\'' +
                '}';
    }
}
