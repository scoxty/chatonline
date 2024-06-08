package com.ncu.chatserver.entity;

import javax.persistence.*;

/**
 * 群组聊天
 */
@Table(name = "imgroup")
public class ImGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	/** 内容 */
	@Column(name = "content")
	private String content;
	/** 用户名 */
	@Column(name = "name")
	private String name;
	/** 头像 */
	@Column(name = "avatar")
	private String avatar;
	/** 时间 */
	@Column(name = "time")
	private String time;
	/** 类型 */
	@Column(name = "type")
	private String type;
	@Column(name = "money")
	private String money;
	@Column(name = "shuliang")
	private String shuliang;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	@Override
	public String toString() {
		return "ImGroup{" +
				"id=" + id +
				", content='" + content + '\'' +
				", name='" + name + '\'' +
				", avatar='" + avatar + '\'' +
				", time='" + time + '\'' +
				", type='" + type + '\'' +
				", money='" + money + '\'' +
				", shuliang='" + shuliang + '\'' +
				'}';
	}
}
