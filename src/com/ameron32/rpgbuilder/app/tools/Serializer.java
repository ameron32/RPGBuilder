package com.ameron32.rpgbuilder.app.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.ameron32.rpgbuilder.npcrecord.NPCRecord;

public class Serializer implements Serializable {
	private static final long serialVersionUID = -4951746889042043974L;

	// untested
	public void WriteAllNPCRecordsToDisk(List<NPCRecord> allNPCRecordsToWrite,
			String path) {
		for (NPCRecord record : allNPCRecordsToWrite) {
			writeOneRecord(record, path);
		}
	}

	public void writeOneRecord(NPCRecord record, String path) {
		try {
			String writePath = path + String.valueOf(record.getId()) + ".npc";
			File directory = new File(path);
			directory.mkdirs();
			FileOutputStream fos = new FileOutputStream(writePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			record.setLastModified(System.currentTimeMillis());
			oos.writeObject(record);
			oos.close();
			fos.close();

		} catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	// untested
	public List<NPCRecord> ReadAllNPCRecordsFromDisk(String directoryPath) {
		List<NPCRecord> allNPCRecords = new ArrayList<NPCRecord>();

		File directory = new File(directoryPath);
		File[] allFiles = directory.listFiles();
		for (File f : allFiles) {
			if (f.getName().contains(".npc")) {
				NPCRecord newNPCRecord = readOneRecord(f.getPath().toString());
				allNPCRecords.add(newNPCRecord);
			}
		}

		return allNPCRecords;
	}

	public NPCRecord readOneRecord(String path) {
		File file = new File(path);
		Long lm = file.lastModified();
		NPCRecord newNPCRecord = null;
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream ois = new ObjectInputStream(fis);

			newNPCRecord = (NPCRecord) ois.readObject();
			ois.close();
			fis.close();
			newNPCRecord.setLastModified(lm);
			return newNPCRecord;
		} catch (StreamCorruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (OptionalDataException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		return newNPCRecord;
}
}
