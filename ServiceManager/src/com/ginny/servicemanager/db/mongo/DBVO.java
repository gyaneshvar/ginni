package com.ginny.servicemanager.db.mongo;

public class DBVO {
	private String eventRef;
	private String alertMode;
	private String alertBriefMsg;
	private String userId;
	private String alertProcessingStatus;
	private Long createDate;
	private Long processingDate;
	private String emailId;
	private String smsId;
	// alert recipient guid
	private Long guid;

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Long processingDate) {
		this.processingDate = processingDate;
	}

	public String getAlertBriefMsg() {
		return alertBriefMsg;
	}

	public void setAlertBriefMsg(String alertBriefMsg) {
		this.alertBriefMsg = alertBriefMsg;
	}

	public String getAlertMode() {
		return alertMode;
	}

	public void setAlertMode(String alertMode) {
		this.alertMode = alertMode;
	}

	public String getAlertProcessingStatus() {
		return alertProcessingStatus;
	}

	public void setAlertProcessingStatus(String alertProcessingStatus) {
		this.alertProcessingStatus = alertProcessingStatus;
	}

	public String getEventRef() {
		return eventRef;
	}

	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

}
