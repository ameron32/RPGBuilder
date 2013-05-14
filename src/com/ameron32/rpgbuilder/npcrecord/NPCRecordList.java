package com.ameron32.rpgbuilder.npcrecord;

import java.util.ArrayList;
import java.util.List;

import com.ameron32.rpgbuilder.app.tools.Tools;

public class NPCRecordList {
	private List<NPCRecord> allNPCRecords;
	private List<Integer> allNPCRecordIds;
	private List<String> allNPCRecordNames;
	private List<Long> allNPCRecordModified;
	private List<String> allNPCRecordJobs;

	public NPCRecordList() {
		allNPCRecords = new ArrayList<NPCRecord>();
		allNPCRecordIds = new ArrayList<Integer>();
		allNPCRecordNames = new ArrayList<String>();
		allNPCRecordModified = new ArrayList<Long>();
		allNPCRecordJobs = new ArrayList<String>();
	}

	public void addRecord(NPCRecord npcRecord) {
		allNPCRecords.add(npcRecord); 
		allNPCRecordIds.add(npcRecord.getId());
		allNPCRecordNames.add(npcRecord.getStatRecord().getName());
		allNPCRecordModified.add(npcRecord.getLastModified());
		allNPCRecordJobs.add(npcRecord.getStatRecord().getJob());
	}

	public NPCRecord getRecordByPosition(int position) {
		NPCRecord recordToReturn = null;
		for (int x = 0; x < allNPCRecords.size(); x++) {
			if (position == x) {
				recordToReturn = allNPCRecords.get(x);
			}
		}
		return recordToReturn;
	}
	
	public NPCRecord getRecordById(int id) {
		NPCRecord record = null;
		for (int x = 0; x < allNPCRecords.size(); x++) {
			if (id == allNPCRecords.get(x).getId()) {
				record = allNPCRecords.get(x);
			}
		}
		return record;
	}
	
	public List<NPCRecord> getRecords() {
		return allNPCRecords;
	}

	public boolean contains(StatRecord sr) {
		boolean contains = false;
		for (NPCRecord npcRecord : allNPCRecords) {
			if (npcRecord.getStatRecord().getId() == sr.getId()) {
				contains = true;
			}
		}
		return contains;
	}
	
	public int findNPCRecordIdByStatRecord(StatRecord sr) {
		int id = -1;
		for (NPCRecord npcRecord : allNPCRecords) {
			if (npcRecord.getStatRecord().getId() == sr.getId()) {
				id = npcRecord.getId();
			}
		}
		return id;
	}
	
	public void clearRecords() {
		allNPCRecords = new ArrayList<NPCRecord>();
		allNPCRecordIds = new ArrayList<Integer>();
		allNPCRecordNames = new ArrayList<String>();
		allNPCRecordModified = new ArrayList<Long>();
		allNPCRecordJobs = new ArrayList<String>();
	}

	public List<String> getStringList() {
		return allNPCRecordNames;
	}

	// region Sorts
	public void sortRecordsOldestFirst() {
		allNPCRecords = Tools.sortLeastToGreatest(allNPCRecordModified,
				allNPCRecords);
		clearAndRegenerate();
	}

	public void sortRecordsNewestFirst() {
		allNPCRecords = Tools.sortGreatestToLeast(allNPCRecordModified,
				allNPCRecords);
		clearAndRegenerate();
	}
	
	public void sortRecordsAFirst() {
		allNPCRecords = Tools.sortAtoZ(allNPCRecordNames,
				allNPCRecords);
		clearAndRegenerate();
	}
	
	public void sortRecordsZFirst() {
		allNPCRecords = Tools.sortZtoA(allNPCRecordNames,
				allNPCRecords);
		clearAndRegenerate();
	}
	
	public void sortRecordsJobAFirst() {
		allNPCRecords = Tools.sortAtoZ(allNPCRecordJobs, allNPCRecords);
		clearAndRegenerate();
	}
	
	public void sortRecordsJobZFirst() {
		allNPCRecords = Tools.sortZtoA(allNPCRecordJobs, allNPCRecords);
		clearAndRegenerate();
	}
	// endregion Sorts
	
	private void clearAndRegenerate() {
		// clear others
		allNPCRecordIds = new ArrayList<Integer>();
		allNPCRecordNames = new ArrayList<String>();
		allNPCRecordModified = new ArrayList<Long>();
		allNPCRecordJobs = new ArrayList<String>();
		// regenerate other lists
		for (NPCRecord r : allNPCRecords) {
			allNPCRecordIds.add(r.getId());
			allNPCRecordNames.add(r.getStatRecord().getName());
			allNPCRecordModified.add(r.getLastModified());
			allNPCRecordJobs.add(r.getStatRecord().getJob());
		}
	}
}
