package com.ginny.servicemanager.db.mongo;

public class CloudServiceDefinitionDBVO {
	String id;
	String name;
	String baseServiceURL;

	public String getBaseServiceURL() {
		return baseServiceURL;
	}

	public void setBaseServiceURL(String implClass) {
		this.baseServiceURL = implClass;
	}

	String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
