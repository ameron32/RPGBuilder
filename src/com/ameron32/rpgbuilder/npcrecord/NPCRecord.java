package com.ameron32.rpgbuilder.npcrecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.npcrecord.components.Note;

public class NPCRecord implements Serializable {
	private static final long serialVersionUID = 5276818602267661440L;

	private int id = -1;
	private int ver = 0;
	private long lastModified;

	public NPCRecord(int id, StatRecord sr) {
		this.id = id;
		this.ver = Assets.ver;
		statRecord = sr;
	}

	public NPCRecord(StatRecord sr) {
		this.id = r.nextInt(100000);
		this.ver = Assets.ver;
		statRecord = sr;
	}

	public String getMyName() {
		return statRecord.getName();
	}

	public String getMyJob() {
		return statRecord.getJob();
	}

	public String getMyNote() {
		Note n = statRecord.getMostRecentNote();
		if (n != null) {
			return n.getNoteString();
		} else {
			return "";
		}
	}
	
	public String getMyCost() {
		return statRecord.getCost() + "";
	}

	public void setLastModified(long lm) {
		lastModified = lm;
	}

	public long getLastModified() {
		return lastModified;
	}

	public int getId() {
		return id;
	}

	public int getVer() {
		return ver;
	}

	private StatRecord statRecord;

	public StatRecord getStatRecord() {
		return statRecord;
	}

	private Random r = new Random();

	@Override
	public String toString() {
		Boolean srNull = true;
		if (statRecord != null)
			srNull = false;

		String s = "id:(" + id + "), statRecord: (" + !srNull + ")" + "\n";
		return s;
	}

	/**
	 * Always treat de-serialization as a full-blown constructor, by validating
	 * the final state of the de-serialized object.
	 */
	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default de-serialization first
		aInputStream.defaultReadObject();
	}

	/**
	 * This is the default implementation of writeObject. Customise if
	 * necessary.
	 */
	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
