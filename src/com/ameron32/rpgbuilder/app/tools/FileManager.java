package com.ameron32.rpgbuilder.app.tools;

import java.io.File;

public class FileManager {

	public void clearDataDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		if (directory != null) {
			File[] allFiles = directory.listFiles();
			for (File f : allFiles) {
				f.delete();
			}
		}
	}

	public void deleteNPCRecord(int recordId, String directoryPath) {
		File directory = new File(directoryPath);
		if (directory != null) {
			File[] allFiles = directory.listFiles();
			for (File f : allFiles) {
				if (f.getName().equalsIgnoreCase(recordId + ".npc")) {
					f.delete();
				}
			}
		}
	}

//	public String whatIsFileName(int recordId, String directoryPath) {
//		StringBuilder sb = new StringBuilder();
//		File directory = new File(directoryPath);
//		if (directory != null) {
//			File[] allFiles = directory.listFiles();
//			for (File f : allFiles) {
//				sb.append(f.getName());
//				sb.append("\n");
//			}
//		}
//		return sb.toString();
//	}

}
