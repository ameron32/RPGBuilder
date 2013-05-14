package com.ameron32.rpgbuilder;

public class Versions {

	public static final Version v130 = new Version(
			130,
			"",
			"Each time you open the application, it will delete the old records. This is because if it attempts to load the application and there are old records (pre-version 0.13) it will fail (everytime). Rather than force you to manually delete the records, I'm doing my clean up during application startup. This means you can't carry over NPCs (in this version) beyond application closing.");
	public static final Version v135 = new Version(
			135,
			"",
			"Not a full release, but WOW! There is a new Asset folder, so /ameron32Projects/Assets. It'll store a file within it, 'adv.csv'. This file contains ALL advantage/disadvantage data. I have nothing forbidden YET! Characters are loaded with random advantages and disadvantages now. This is still a dumb generation and isn't editable yet. It is coming soon! Please have patience with me. Oh! And be connected to the internet when you click 'Create Directories' because it will download the .csv file you need from the internet. AWESOME!"
					+ "\n"
					+ "\n"
					+ "What's Missing?"
					+ "\n"
					+ "- Cost does not reflect advantages and disadvantages. In fact, no stats change with them. They are just words so far."
					+ "\n"
					+ "- Forbidden adv/disadv are being chosen. I wanted to ensure all went as expected. Soon..."
					+ "\n"
					+ "- Can't edit adv/disadv yet."
					+ "\n"
					+ "- Basically, there is a lot more about them left to implement."
					+ "\n"
					+ "- I imagine that the new downloader isn't as explanatory as it should be. As a first, release feature... don't be surprised that I expect to do more with it over the next releases."
					+ "\n"
					+ "- Advantages are not deleted on close. Let's not hit the servers more than necessary.");
	public static final Version v140 = new Version(
			140,
			"",
			"THIS SCREEN WILL NOT SHOW TWICE. "
					+ "\n"
					+ "\n"
					+ "This new version has improved the basic components of the app. Namely, now NPCRecords have more information. Advantages, Disadvantages, Perks, and Quirks are loading now. These do NOT yet impact core stats. They only effect Point Cost for the character. WARNING: if an advantage shows a cost of 0, this adv/disadv/perk/quirk could not be calculated through typical means. You'll have to look it up. However, you can change this cost, as you would the characters ST, IQ, DX, or HT stats in the Edit Record screen."
					+ "\n"
					+ "\n"
					+ "Advantages are automatically downloaded from Dropbox if you don't already have a copy on the device. More ahead!"
					+ "\n"
					+ "\n"
					+ "What's New"
					+ "\n"
					+ "- Fully editable advantages (change advantages or just their cost)"
					+ "\n"
					+ "- Same process to create and edit characters as before"
					+ "\n" + "- Complete directory creation and a 1-time");
	public static final Version v145 = new Version(145, "", "missing");
	public static final Version v150 = new Version(
			150,
			"",
			"Massive improvements to FEATURES (the details about the characters), and matching genders and jobs. There has been a giant overhaul of the jobs system since the last major release. In short, there is a weighted system to determine who you met. Commoners are much more likely, making randomly meeting a high ranking member more impressive."
					+ "Advantages and Features are fully editable:"
					+ "\n"
					+ "Change an advantage to a new random advantage, add another advantage or delete an existing one with 1-click."
					+ "\n"
					+ "Same with features."
					+ "\n"
					+ "Tablet customizations improved. ");
	public static final Version v153 = new Version(
			153,
			"",
			"Testing new sidebar. When finished, this new sidebar will be used to switch between builder apps. For now it does nothing...");
	
	public static final Version allVersions[] = { v153, v150, v145, v140, v135, v130 };

	public static boolean hasNotes(int ver) {
		boolean hasNotes = false;
		for (int v = 0; v < allVersions.length; v++) {
			Version version = allVersions[v];
			if (version.getNumber() == ver) {
				hasNotes = true;
			}
		}
		return hasNotes;
	}

	public static String versionNotes(int ver) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < allVersions.length; i++) {
			Version v = allVersions[i];
			if (v.getNumber() == ver) {
				sb.append(v.getNumber() + ":" + "\n");
				sb.append(v.getTitle() + "\n");
				sb.append(v.getNotes() + "\n" + "\n");
			}
		}
		return sb.toString();
	}

	public static String previousNotes(int ver) {
		StringBuilder sb = new StringBuilder();
		if (hasNotes(ver)) {
			for (Version v : allVersions) {
				if (v.getNumber() != ver) {
					sb.append("0." + v.getNumber() + ":" + "\n");
					sb.append(v.getTitle() + "\n");
					sb.append(v.getNotes() + "\n" + "\n");
				}
			}
		} else {
			for (Version v : allVersions) {
				sb.append("0." + v.getNumber() + ":" + "\n");
				sb.append(v.getTitle() + "\n");
				sb.append(v.getNotes() + "\n" + "\n");
			}
		}
		return sb.toString();
	}

}
