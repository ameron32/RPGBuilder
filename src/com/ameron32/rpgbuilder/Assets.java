package com.ameron32.rpgbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

import com.ameron32.rpgbuilder.advantageaddon.Advantage;

public class Assets {

	// Application version number
	public static int ver = 153;
	// change Assets.advantagesCSV, ensure advVER.csv exists on Dropbox.
	// NPCRecords and StatRecords have versions embedded in them
	// Version Notes associate with ver

	// Configuration preferences
	public static final boolean noForbidden = true;
	public static final boolean wipeFilesOnStart = false;

	// All directories to be used stored here
	// Main directory
	public static String directoryPath = Environment
			.getExternalStorageDirectory().getPath() + "/ameron32Projects/";
	// Subfolders
	public static String assetPath = directoryPath + "Assets/";
	// All needed creatable directories
	public static String debugDirPath = assetPath + "Debug/";
	public static String[] allDirectories = { directoryPath, assetPath, debugDirPath };

	// All files to be used stored here
	public static String advantagesCSV = assetPath + "adv" + Assets.ver
			+ ".csv";
	// All downloadable files
	public static String[] allFiles = { advantagesCSV };

	public static Boolean everythingExists = false;

	
	public static String currentVersion = "0." + ver;
	public static String currentVersionNotes = Versions.versionNotes(ver); 
	public static String previousVersionNotes = Versions.previousNotes(ver);

	public static String directoriesExplanation = "RPG Builder needs some folders and files to function.";
	public static String moreDescription = "The folders and files marked [!] are missing, but required. These folders will be created on your internal storage or sdcard. These files will be downloaded from the internet. Please enable internet before proceeding.";

	private static String getDirectoriesList() {
		StringBuilder sb = new StringBuilder();
		File f;
		for (int x = 0; x < allDirectories.length; x++) {
			f = new File(allDirectories[x]);
			if (!f.canWrite()) {
				sb.append("[!] ");
			}
			sb.append(allDirectories[x]);
			sb.append("\n");
		}
		return sb.toString();
	}

	private static String getFilesList() {
		StringBuilder sb = new StringBuilder();
		File f;
		for (int x = 0; x < allFiles.length; x++) {
			f = new File(allFiles[x]);
			if (!f.canWrite()) {
				sb.append("[!]  ");
			}
			sb.append(allFiles[x]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String directoriesListString = getDirectoriesList();;
	public static String filesListString = getFilesList();
	public static String assetsListString = moreDescription + "\n" + "\n"
			+ getDirectoriesList() + "\n" + getFilesList();
	
	public static List<Advantage> advantages = new ArrayList<Advantage>();

}
