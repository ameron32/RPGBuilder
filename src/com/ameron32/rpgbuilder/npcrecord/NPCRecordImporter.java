package com.ameron32.rpgbuilder.npcrecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.advantageaddon.CsvReader;
import com.ameron32.rpgbuilder.app.tools.Debugger;
import com.ameron32.rpgbuilder.app.tools.Tools;
import com.ameron32.rpgbuilder.npcrecord.components.Gender;
import com.ameron32.rpgbuilder.npcrecord.components.Note;
import com.ameron32.rpgbuilder.npcrecord.components.NoteType;

public class NPCRecordImporter {
	public List<NPCRecord> readAllNPCRecords(String path) {
		List<NPCRecord> nrs = new ArrayList<NPCRecord>();

		try {
			int ver = Assets.ver;

			CsvReader nrReader = new CsvReader(path + ".csv");

			nrReader.readHeaders();

			while (nrReader.readRecord()) {
				/*
				 * int id = readInt(nrReader.get("id")); String aORd =
				 * nrReader.get("aORd"); String nameString =
				 * nrReader.get("nameString"); String advTypeString =
				 * nrReader.get("advTypeString"); String superTypeString =
				 * nrReader.get("superTypeString"); String cost =
				 * nrReader.get("cost"); int pageInt =
				 * readInt(nrReader.get("pageInt")); boolean isLeveled =
				 * readBoolean(nrReader.get("isLeveled")); boolean hasNotes =
				 * readBoolean(nrReader.get("hasNotes")); boolean isFakeCost =
				 * readBoolean(nrReader.get("isFakeCost")); int calcCost =
				 * readInt(nrReader.get("calcCost")); boolean isPhysical =
				 * readBoolean(nrReader.get("isPhysical")); boolean isMental =
				 * readBoolean(nrReader.get("isMental")); boolean isSocial =
				 * readBoolean(nrReader.get("isSocial")); boolean isExotic =
				 * readBoolean(nrReader.get("isExotic")); boolean isSuper =
				 * readBoolean(nrReader.get("isSuper")); boolean isMundane =
				 * readBoolean(nrReader.get("isMundane")); boolean isForbidden =
				 * readBoolean(nrReader.get("isForbidden")); // FIXME I cheated
				 * to get the front and back off of the // description. // Once
				 * proper punctuation is handled, this will cause problems.
				 * String description = nrReader.get("description").substring(3,
				 * nrReader.get("description").length() - 3);
				 * 
				 * Advantage thisAdv = new Advantage(id, ver, aORd, nameString,
				 * advTypeString, superTypeString, cost, pageInt, isLeveled,
				 * hasNotes, isFakeCost, calcCost, isPhysical, isMental,
				 * isSocial, isExotic, isSuper, isMundane, isForbidden,
				 * description);
				 */
				int npcId = ver;
				int npcVersion = readInt(nrReader.get("npcVersion"));
				long npcLastModified = readLong(nrReader.get("npcLastModified"));

				int id = readInt(nrReader.get("id"));
				ver = ver;
				int st = readInt(nrReader.get("st"));
				int dx = readInt(nrReader.get("dx"));
				int iq = readInt(nrReader.get("iq"));
				int ht = readInt(nrReader.get("ht"));
				int hp = readInt(nrReader.get("hp"));
				int will = readInt(nrReader.get("will"));
				int per = readInt(nrReader.get("per"));
				int fp = readInt(nrReader.get("fp"));
				int dodge = readInt(nrReader.get("dodge"));
				int bm = readInt(nrReader.get("bm"));
				int bl = readInt(nrReader.get("bl"));
				double bs = readDouble(nrReader.get("bs"));
				int cost = readInt(nrReader.get("cost"));
				String name = nrReader.get("name");
				String job = nrReader.get("job");
				List<Advantage> advs = makeAdvantageRecord(nrReader.get("advs"));
				List<String> features = makeFeatures(nrReader
						.get("features"));
				Gender gender = Gender.valueOf(nrReader.get("gender"));
				List<Note> notes = makeNotes(nrReader.get("notes"));

				StatRecord newSR = new StatRecord(id, ver, st, dx, iq, ht, hp, will, per, fp, dodge, bm, bl, bs, cost, gender, name, job, advs, features);
				newSR.addNotes(notes);
				NPCRecord newNPCR = new NPCRecord(npcId, newSR);
				nrs.add(newNPCR);
			}

			nrReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Debugger.addToDebuggerText("NPCLoader failed FNF: " + e.toString(),
					true);
		} catch (IOException e) {
			e.printStackTrace();
			Debugger.addToDebuggerText("NPCLoader failed IO: " + e.toString(),
					true);
		}
		return nrs;
	}

	private boolean readBoolean(String s) {
		boolean booleanIs;
		if (s.equalsIgnoreCase("true")) {
			booleanIs = true;
		} else {
			booleanIs = false;
		}
		return booleanIs;
	}

	private int readInt(String s) {
		int intIs;
		intIs = Integer.decode(s);
		return intIs;
	}

	private long readLong(String s) {
		long longIs;
		longIs = Long.decode(s);
		return longIs;
	}

	private double readDouble(String s) {
		double doubleIs;
		doubleIs = Double.parseDouble(s);
		return doubleIs;
	}

//	private int numberOfBars(String s) {
//		int numberOfBars = 0;
//		for (int i = 0; i < s.length(); i++) {
//			String x = Character.toString(s.charAt(i));
//			if (x.equals("|")) {
//				numberOfBars++;
//			}
//		}
//		return numberOfBars;
//	}

	private boolean isBar(char c) {
		String s = Character.toString(c);
		if (s.equals("|")) {
			return true;
		} else {
			return false;
		}
	}

	private List<Note> makeNotes(String allNotes) {
		List<Note> notes = new ArrayList<Note>();
		for (String s: makeStrings(allNotes)) {
			notes.add(new Note(-1, NoteType.undefined, s));
		}
		return notes;
		
	}

	private List<String> makeFeatures(String allFeatures) {
		List<String> features = new ArrayList<String>();
		for (String s : makeStrings(allFeatures)) {
			features.add(s);
		}
		return features;
 	}

	private List<String> makeStrings(String allStrings) {
		List<String> strings = new ArrayList<String>();
		String s;
		int firstNoteChar = 0;
		for (int j = 0; j < allStrings.length(); j++) {
			if (isBar(allStrings.charAt(j))) {
				// FIXME does this work???
				s = allStrings.substring(firstNoteChar, j);
				strings.add(s);
				firstNoteChar = j + 1;
			}
		}
		return strings;
	}
	
	private List<Advantage> makeAdvantageRecord(String allAdvantages) {
		List<Advantage> advantages = new ArrayList<Advantage>();
		for (String s: makeStrings(allAdvantages)) {
			Tools.findAdvantageById(readInt(s.substring(0, 4)));
		}
		return advantages;
	}
}
