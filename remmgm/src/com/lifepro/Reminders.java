package com.lifepro;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Reminders {
	String MIN_ID = new String(new char[] { 0 });// "\0"

	SortedSet<RemData> remset = Collections
			.synchronizedSortedSet(new TreeSet<RemData>());

	static class RemData implements Comparable<RemData> {
		// id are expected to be unique for consistent result
		String remId;
		Long scheduledAtTimeInMillis;

		@Override
		public int compareTo(RemData o2) {

			if (this.remId.equals(o2.remId))
				return 0;
			int res = this.scheduledAtTimeInMillis
					.compareTo(o2.scheduledAtTimeInMillis);
			return res == 0 ? this.remId.compareTo(o2.remId) : res;

		}

		public boolean equals(RemData o2) {

			return this.remId.equals(o2.remId);
		}

		public RemData(String remId, Long scheduledAtTimeInMillis) {
			super();
			this.remId = remId;
			this.scheduledAtTimeInMillis = scheduledAtTimeInMillis;
		}

		public String toString() {
			return remId + ":" + scheduledAtTimeInMillis + ";  ";
		}

	}

	public void add(RemData data) {
		this.remset.add(data);
	}

	public void addAll(List<RemData> data) {
		this.remset.addAll(data);
	}

	SortedSet<RemData> findRemindersScheduledBetween(long startTs, long endTs) {
		return remset.subSet(new RemData(MIN_ID, startTs), new RemData(MIN_ID,
				endTs));

	}

	private static void perfTest(String[] args) throws Exception {
		int numRecord = Integer.parseInt(args[0]);

		Reminders rem = new Reminders();
		RemData remData;
		long ts1 = System.currentTimeMillis();
		for (int i = 0; i < numRecord; i++) {
			remData = new RemData(i + "", System.currentTimeMillis());
			rem.add(remData);

		}
		long ts2 = System.currentTimeMillis();

		int counts = 0;

		long start = ts1;

		for (int i = 1; i <= ts2 - ts1 + 1; i++) {

			SortedSet<RemData> val = rem.findRemindersScheduledBetween(start,
					start + 1);
			// System.out.println("query time range=" + start + "," + (start +
			// 1)+ "-res=" + val);
			start += 1;
			counts += val.size();
			// if (counts > 100)
			// break;
		}
		long ts3 = System.currentTimeMillis();

		System.out.println("Number Of Record Inserted=" + numRecord
				+ " Num of record searched=" + counts
				+ " num of record inserted in 1ms="
				+ (numRecord / (ts2 - ts1 + 1)) + " num of queries="
				+ (ts2 - ts1) + " num of queies in 1ms="
				+ ((ts2 - ts1 + 1) / (ts3 - ts2 + 1)));
	}

	public static void main(String[] args) {
		try {
			perfTest(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
