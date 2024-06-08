package com.ncu.chatserver.entity;

/**
 * 好友
 */
public class Hy {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 用户
     */
    private Integer userId;

    /**
     * 好友用户
     */
    private Integer hyUserId;

    /**
     * 内容
     */
    private String nr;

    /**
     * 状态
     */
    private String zt;

    /**
     * 分组名
     */
    private String hyGroupName;

    public String getHyGroupName() {
        return hyGroupName;
    }

    public void setHyGroupName(String hyGroupName) {
        this.hyGroupName = hyGroupName;
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

    public Integer getHyUserId() {
        return hyUserId;
    }

    public void setHyUserId(Integer hyUserId) {
        this.hyUserId = hyUserId;
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
}
