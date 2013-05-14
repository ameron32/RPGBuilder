package com.ameron32.rpgbuilder.app.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.CharacterBuilder;
import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.advantageaddon.AdvantageEditor;
import com.ameron32.rpgbuilder.npcrecord.NPCRecord;
import com.ameron32.rpgbuilder.npcrecord.NPCRecordExporter;
import com.ameron32.rpgbuilder.npcrecord.NPCRecordImporter;
import com.ameron32.rpgbuilder.npcrecord.NPCRecordList;
import com.ameron32.rpgbuilder.npcrecord.StatRecord;
import com.ameron32.rpgbuilder.npcrecord.components.Note;

public class Tools {

	// decodes Doubles and Integers but returns a 0 if it fails
	public static int sanitizeString(String s) {
		if (s.contains(".")) {
			try {
				return (int) Math.round(Double.valueOf(s));
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			try {
				return Integer.decode(s);
			} catch (Exception e) {
				return -1;
			}
		}
	}

	public static String buildString(List<String> strings, boolean comma,
			boolean newLine) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s);
			if (comma)
				sb.append(", ");
			if (newLine)
				sb.append("\n");
		}
		return sb.toString();
	}

	// random number with greater probability close to 10
	public static int normalizedRandom() {
		Random r = new Random();
		// chance for average 8-12 50%
		// chance for above-below average 6-14 45%
		// chance for ridiculous 3-17 5%
		int chance = r.nextInt();
		if (chance > 0.5) {
			// average
			return r.nextInt(6) + 1 + 7;
		} else if (chance > 0.05) {
			// above-below average
			return r.nextInt(10) + 1 + 5;
		} else {
			// ridiculous
			return r.nextInt(16) + 1 + 2;
		}
	}

	// random number with greater probability close to 10
	public static int normalizedRandomOf(int max) {
		Random r = new Random();
		// chance for average 8-12 50%
		// chance for above-below average 6-14 45%
		// chance for ridiculous 3-17 5%
		int chance = r.nextInt();
		int returnMe;
		double returnMeD;
		if (chance > 0.5) {
			// average
			returnMeD = r.nextInt(6) + 1 + 7;
		} else if (chance > 0.05) {
			// above-below average
			returnMeD = r.nextInt(10) + 1 + 5;
		} else {
			// ridiculous
			returnMeD = r.nextInt(16) + 1 + 2;
		}
		double maxD = max;
		returnMeD = returnMeD / 20.0 * maxD;
		returnMe = (int) Math.round(returnMeD);
		return returnMe;
	}

	public static int randomId() {
		Random r = new Random();
		int id = r.nextInt(100000);
		return id;
	}

	public static void saveFile(StatRecord sr, int id, int ver, String path) {
		Serializer s = new Serializer();
		NPCRecord newRecord = new NPCRecord(id, sr);
		s.writeOneRecord(newRecord, path);
	}

	// public static void saveFile(NPCRecord nr, String path) {
	// Serializer s = new Serializer();
	// s.writeOneRecord(nr, path);
	// }

	public static boolean loadFileSuccessful(String path) {
		Serializer s = new Serializer();
		StatRecord sr;
		boolean returnMe;
		try {
			sr = s.readOneRecord(path).getStatRecord();
			returnMe = true;
		} catch (NullPointerException e) {
			e.printStackTrace();
			returnMe = false;
		}
		return returnMe;
	}

	public static StatRecord loadFile(String path) {
		Serializer s = new Serializer();
		StatRecord sr = s.readOneRecord(path).getStatRecord();
		return sr;
	}

	static List<NPCRecord> nrs;
	public static List<NPCRecord> loadNPCRecordsFromDir(String directoryPath,
			int ver) {
		Serializer s = new Serializer();
		nrs = new ArrayList<NPCRecord>();
		if (s.ReadAllNPCRecordsFromDisk(directoryPath) != null) {
			nrs = s.ReadAllNPCRecordsFromDisk(directoryPath);
		}

		// Version comparing
//		for (NPCRecord nr : nrs) {
//			int nrVersion = nr.getVer();
//			StatRecord sr = nr.getStatRecord();
//			int srVersion = sr.getVer();
//			String error = "";
//			if (nrVersion != srVersion) {
//				error = "[!]";
//			}
//			String text = sr.getName() + " vN(" + nrVersion + ") vS("
//					+ srVersion + ")" + error;
//			Debugger.addToDebuggerText(text, true);
//		}
		return nrs;
	}
	
	public static NPCRecord loadNPCRecord(int id) {
		// TODO needs to load first?
		for (NPCRecord n : nrs) {
			if (id == n.getId()) {
				return n;
			}
		}
		return null;
	}

	public static void clearDataDirectory() {
		FileManager fm = new FileManager();
		fm.clearDataDirectory(Assets.directoryPath);
	}

	public static Advantage findAdvantageById(int id) {
		Advantage advantage = null;
		for (Advantage a : Assets.advantages) {
			if (a.getId() == id) {
				advantage = a;
			}
		}
		return advantage;
	}

	public static Advantage findAdvantageByName(String name) {
		Advantage advantage = null;
		for (Advantage a : Assets.advantages) {
			if (a.getNameString().equalsIgnoreCase(name)) {
				advantage = a;
			}
		}
		return advantage;
	}

	public static void deleteNPCRecord(int recordId) {
		FileManager fm = new FileManager();
		fm.deleteNPCRecord(recordId, Assets.directoryPath);
	}

	public static NPCRecord upgradeNPCRecord(int recordId) {
		// TODO Auto-generated method stub
		NPCRecordExporter exporter = new NPCRecordExporter();
		NPCRecordImporter importer = new NPCRecordImporter();
		// FIXME using Debug dir
		List<NPCRecord> x = new ArrayList<NPCRecord>();
		x.add(loadNPCRecord(recordId));
		boolean fileWrite = exporter.writeAllNPCRecords(x, Assets.debugDirPath + "ex", false);
		if (fileWrite) {
			x = importer.readAllNPCRecords(Assets.debugDirPath + "ex");
		}
		NPCRecord same = null;
		for (NPCRecord n : x) {
			if (n.getId() == recordId) {
				same = n;
			}
		}
		return same;
		
	}

	public static int nextAvailableFileNumber() {
		int x = 0;
		File f = new File(Assets.directoryPath + x + ".npc");
		while (f.exists()) {
			x++;
			f = new File(Assets.directoryPath + x + ".npc");
		}
		return x;
	}

	public static List<NPCRecord> sortLeastToGreatest(List<Long> l,
			List<NPCRecord> toSort) {
		int totalnumbers = l.size();
		Long[] array = l.toArray(new Long[] {});
		long temp;
		NPCRecord tempRecord;

		List<NPCRecord> sorting = toSort;
		// bubble sort
		for (int i = 0; i < totalnumbers - 1; i++) {
			for (int j = i + 1; j < totalnumbers; j++) {
				if (array[i] > array[j]) {
					temp = array[i];
					tempRecord = sorting.get(i);

					array[i] = array[j];
					sorting.set(i, sorting.get(j));

					array[j] = temp;
					sorting.set(j, tempRecord);
				}
			}
		}

		List<NPCRecord> sortedRecords = sorting;
		return sortedRecords;
	}

	public static List<NPCRecord> sortGreatestToLeast(List<Long> l,
			List<NPCRecord> toSort) {
		List<NPCRecord> sorting = sortLeastToGreatest(l, toSort);
		Collections.reverse(sorting);
		List<NPCRecord> sorted = sorting;
		return sorted;

	}

	public static List<NPCRecord> sortAtoZ(List<String> s,
			List<NPCRecord> toSort) {
		String[] x = s.toArray(new String[] {});
		List<NPCRecord> sorting = toSort;

		// bubble sort
		int j;
		boolean flag = true; // will determine when the sort is finished
		String temp;
		NPCRecord tempRecord;

		while (flag) {
			flag = false;
			for (j = 0; j < x.length - 1; j++) {
				if (x[j].compareToIgnoreCase(x[j + 1]) > 0) { // ascending sort
					temp = x[j];
					tempRecord = sorting.get(j);

					x[j] = x[j + 1]; // swapping
					sorting.set(j, sorting.get(j + 1));

					x[j + 1] = temp;
					sorting.set(j + 1, tempRecord);

					flag = true;
				}
			}
		}

		List<NPCRecord> sorted = sorting;
		return sorted;
	}

	public static List<NPCRecord> sortZtoA(List<String> s,
			List<NPCRecord> toSort) {
		List<NPCRecord> sorting = sortAtoZ(s, toSort);
		Collections.reverse(sorting);
		List<NPCRecord> sorted = sorting;
		return sorted;
	}

	public static List<String> sortOldestToNewest(List<Note> ln) {
		int totalnumbers = ln.size();
		List<Long> createdTimes = new ArrayList<Long>();
		for (Note n : ln) {
			createdTimes.add(n.getCreationTime());
		}

		Long[] array = createdTimes.toArray(new Long[] {});
		long temp;
		Note tempRecord;

		List<Note> sorting = ln;
		// bubble sort
		for (int i = 0; i < totalnumbers - 1; i++) {
			for (int j = i + 1; j < totalnumbers; j++) {
				if (array[i] > array[j]) {
					temp = array[i];
					tempRecord = sorting.get(i);

					array[i] = array[j];
					sorting.set(i, sorting.get(j));

					array[j] = temp;
					sorting.set(j, tempRecord);
				}
			}
		}

		List<Note> sortedNotes = sorting;
		List<String> sortedNoteStrings = new ArrayList<String>();
		for (Note n : sortedNotes) {
			sortedNoteStrings.add(n.getNoteString());
		}
		return sortedNoteStrings;
	}

	public static List<String> sortNewestToOldest(List<Note> ln) {
		List<String> sorting = sortOldestToNewest(ln);
		Collections.reverse(sorting);
		List<String> sorted = sorting;
		return sorted;
	}

	// public static List<String> sortLeastToGreatest(List<Long> l) {
	// int totalnumbers = l.size();
	// Long[] array = l.toArray(new Long[] {});
	// long temp;
	// NPCRecord tempRecord;
	//
	// List<NPCRecord> sorting = toSort;
	// // bubble sort
	// for (int i = 0; i < totalnumbers - 1; i++) {
	// for (int j = i + 1; j < totalnumbers; j++) {
	// if (array[i] > array[j]) {
	// temp = array[i];
	// tempRecord = sorting.get(i);
	//
	// array[i] = array[j];
	// sorting.set(i, sorting.get(j));
	//
	// array[j] = temp;
	// sorting.set(j, tempRecord);
	// }
	// }
	// }
	//
	// List<NPCRecord> sortedRecords = sorting;
	// return sortedRecords;
	// }

	public static void loadAdvantages() {
		if (Assets.everythingExists) {
			Assets.advantages = AdvantageEditor
					.readAllAdvantages(Assets.advantagesCSV);
		}
	}

	public static String exportAllCharacters(NPCRecordList nl) {
		String savePath = Assets.debugDirPath
				+ "export" + new Random().nextInt(10000);
		boolean success = false;
		if (nl != null) {
			NPCRecordExporter nre = new NPCRecordExporter();
			// TODO temporary exporter debug directory
			success = nre.writeAllNPCRecords(nl.getRecords(), savePath, true);
		}
		if (success) {
			return "All records exported to " + savePath + "!";
		} else {
			return "Export failed.";
		}
	}

	public static void createDirectory(String path) {
		File directory = new File(path);
		directory.mkdirs();
	}

	public static boolean whatsNewExists() {
		File f = new File(Assets.assetPath + "firstRun" + Assets.ver + ".dun");
		return f.exists();
	}

	public static void whatsNewCreate() {
		File f = new File(Assets.assetPath + "firstRun" + Assets.ver + ".dun");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void preserveCreate() {
		File f = new File(Assets.assetPath + "preserve.all");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void preserveRemove() {
		File f = new File(Assets.assetPath + "preserve.all");
		f.delete();
	}

	public enum Sort {
		nameAZ, nameZA, recent01, recent10, id01, id10, jobAZ, jobZA, none
	}

	public static String sortNames(Sort name) {
		String properName = "";
		switch (name) {
		case nameAZ:
			properName = "Alphabetical Name A-to-Z";
			break;
		case nameZA:
			properName = "Alphabetical Name Z-to-A";
			break;
		case recent01:
			properName = "Oldest First";
			break;
		case recent10:
			properName = "Most Recent First";
			break;
		case jobAZ:
			properName = "Alphabetical Class A-to-Z";
			break;
		case jobZA:
			properName = "Alphabetical Class Z-to-A";
			break;
		case none:
			properName = "No Sorting";
			break;
		default:
			break;
		}
		return properName;
	}
	
	public static boolean isActionLeft(float startX, float finalX, float deadzone) {
		boolean is = false;
		if ((startX - finalX) > deadzone) {
			if (finalX < startX) {
				is = true;
			}
		}
		return is;
	}

	public static boolean isActionRight(float startX, float finalX, float deadzone) {
		boolean is = false;
		if ((finalX - startX) > deadzone) {
			if (finalX > startX) {
				is = true;
			}
		}
		return is;
	}

	public static boolean isActionDown(float startY, float finalY, float deadzone) {
		boolean is = false;
		if ((startY - finalY) > deadzone) {
			if (finalY < startY) {
				is = true;
			}
		}
		return is;
	}

	public static boolean isActionUp(float startY, float finalY, float deadzone) {
		boolean is = false;
		if ((finalY - startY) > deadzone) {
			if (finalY > startY) {
				is = true;
			}
		}
		return is;
	}
}
