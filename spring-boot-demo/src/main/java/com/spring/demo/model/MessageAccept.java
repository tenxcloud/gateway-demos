/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2020/8/6 @author peiyun
 */

package com.spring.demo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageAccept {

	String name;
	String num;
	String lala;
	public MessageAccept(){}
	public MessageAccept(String name, String num, String lala) {
		super();
		this.name = name;
		this.num = num;
		this.lala = lala;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getLala() {
		return lala;
	}
	public void setLala(String lala) {
		this.lala = lala;
	}

}
