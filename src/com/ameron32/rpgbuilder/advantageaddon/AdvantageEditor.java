package com.ameron32.rpgbuilder.advantageaddon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.app.tools.Debugger;

/**
 * 
 * @author klemeilleur
 * 
 * 
 */

public class AdvantageEditor {

	public static List<Advantage> readAllAdvantages(String path) {
		List<Advantage> allAdvs = new ArrayList<Advantage>();

		try {
			int ver = Assets.ver;

			CsvReader advs = new CsvReader(path);

			advs.readHeaders();

			while (advs.readRecord()) {
				int id = readInt(advs.get("id"));
				String aORd = advs.get("aORd");
				String nameString = advs.get("nameString");
				String advTypeString = advs.get("advTypeString");
				String superTypeString = advs.get("superTypeString");
				String cost = advs.get("cost");
				int pageInt = readInt(advs.get("pageInt"));
				boolean isLeveled = readBoolean(advs.get("isLeveled"));
				boolean hasNotes = readBoolean(advs.get("hasNotes"));
				boolean isFakeCost = readBoolean(advs.get("isFakeCost"));
				int calcCost = readInt(advs.get("calcCost"));
				boolean isPhysical = readBoolean(advs.get("isPhysical"));
				boolean isMental = readBoolean(advs.get("isMental"));
				boolean isSocial = readBoolean(advs.get("isSocial"));
				boolean isExotic = readBoolean(advs.get("isExotic"));
				boolean isSuper = readBoolean(advs.get("isSuper"));
				boolean isMundane = readBoolean(advs.get("isMundane"));
				boolean isForbidden = readBoolean(advs.get("isForbidden"));
				// FIXME I cheated to get the front and back off of the
				// description.
				// Once proper punctuation is handled, this will cause problems.
				String description = advs.get("description").substring(3,
						advs.get("description").length() - 3);

				Advantage thisAdv = new Advantage(id, ver, aORd, nameString,
						advTypeString, superTypeString, cost, pageInt,
						isLeveled, hasNotes, isFakeCost, calcCost, isPhysical,
						isMental, isSocial, isExotic, isSuper, isMundane,
						isForbidden, description);
				allAdvs.add(thisAdv);
			}

			advs.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Debugger.addToDebuggerText("AdvEdit failed FNF: " + e.toString(),
					true);
		} catch (IOException e) {
			e.printStackTrace();
			Debugger.addToDebuggerText("AdvEdit failed IO: " + e.toString(),
					true);
		}
		return allAdvs;
	}

	private static boolean readBoolean(String s) {
		boolean booleanIs;
		if (s.equalsIgnoreCase("true")) {
			booleanIs = true;
		} else {
			booleanIs = false;
		}
		return booleanIs;
	}

	private static int readInt(String s) {
		int intIs;
		intIs = Integer.decode(s);
		return intIs;
	}

	// public void WriteAllItems(List<Item> allItemsToWrite, String path,
	// Boolean replaceYN) {
	//
	// String outputFile = path;
	//
	// // before we open the file check to see if it already exists
	// boolean alreadyExists = new File(outputFile).exists();
	//
	// try {
	// // use FileWriter constructor that specifies open for appending
	// CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile + ".csv",
	// true), ',');
	//
	// // if the file didn't already exist then we need to write out the header
	// line
	// if (!alreadyExists)
	// {
	// csvOutput.write("Name");
	// csvOutput.write("ID");
	// csvOutput.write("Gold");
	// csvOutput.write("Type");
	// csvOutput.write("Damage");
	// csvOutput.write("Resistance");
	// csvOutput.endRecord();
	// } else if (replaceYN) {
	// Random r = new Random();
	// csvOutput = new CsvWriter(new FileWriter(outputFile + r.nextInt(100)
	// +".csv", true), ',');
	//
	// csvOutput.write("Name");
	// csvOutput.write("ID");
	// csvOutput.write("Gold");
	// csvOutput.write("Type");
	// csvOutput.write("Damage");
	// csvOutput.write("Resistance");
	// csvOutput.endRecord();
	// }
	// // else assume that the file already has the correct header line
	//
	// // write out a few records
	// for (Item item: allItemsToWrite) {
	// csvOutput.write(item.getName());
	// csvOutput.write(String.valueOf(item.getId()));
	// csvOutput.write(String.valueOf(item.getGoldValue()));
	// csvOutput.write(item.getType());
	// csvOutput.write(String.valueOf(item.getDamage()));
	// csvOutput.write(String.valueOf(item.getResistance()));
	// csvOutput.endRecord();
	// }
	//
	// csvOutput.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

}