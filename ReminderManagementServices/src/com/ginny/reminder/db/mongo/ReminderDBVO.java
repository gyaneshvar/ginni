package com.ginny.reminder.db.mongo;

public class ReminderDBVO {
	String id;
	String userId;
	String label;
	Long firstOccurrenceTimeInMillisSinceEpoch;
	Long lastOccurrenceTimeInMillisSinceEpoch;
	Long nextOccurrenceTimeInMillisSinceEpoch;
	Occurrence occurrence; // "="once|daily|weekdays|weekends|weekly|monthly|yearly"
	Long createTs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getFirstOccurrenceTimeInMillisSinceEpoch() {
		return firstOccurrenceTimeInMillisSinceEpoch;
	}

	public void setFirstOccurrenceTimeInMillisSinceEpoch(
			Long firstOccurrenceTimeInMillisSinceEpoch) {
		this.firstOccurrenceTimeInMillisSinceEpoch = firstOccurrenceTimeInMillisSinceEpoch;
	}

	public Long getLastOccurrenceTimeInMillisSinceEpoch() {
		return lastOccurrenceTimeInMillisSinceEpoch;
	}

	public void setLastOccurrenceTimeInMillisSinceEpoch(
			Long lastOccurrenceTimeInMillisSinceEpoch) {
		this.lastOccurrenceTimeInMillisSinceEpoch = lastOccurrenceTimeInMillisSinceEpoch;
	}

	public Occurrence getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
	}

	public Long getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Long createTs) {
		this.createTs = createTs;
	}

	public Long getNextOccurrenceTimeInMillisSinceEpoch() {
		return nextOccurrenceTimeInMillisSinceEpoch;
	}

	public void setNextOccurrenceTimeInMillisSinceEpoch(
			Long nextOccurrenceTimeInMillisSinceEpoch) {
		this.nextOccurrenceTimeInMillisSinceEpoch = nextOccurrenceTimeInMillisSinceEpoch;
	}

}
