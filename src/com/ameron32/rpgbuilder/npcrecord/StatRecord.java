package com.ameron32.rpgbuilder.npcrecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.app.tools.Tools;
import com.ameron32.rpgbuilder.app.tools.Tools.Sort;
import com.ameron32.rpgbuilder.npcrecord.components.Gender;
//import java.util.ArrayList;
//import java.util.List;
import com.ameron32.rpgbuilder.npcrecord.components.Note;

public class StatRecord implements Serializable {
	private static final long serialVersionUID = -8893798094583173489L;

	private int ST, DX, IQ, HT, HP, Will, Per, FP, Dodge, BM, BL, cost = 0;
	private int ver = 0;
	private int id = -1;
	private double BS = 0.0;
	private String name = null;
	private String job = null;
	private List<Advantage> advantageRecord;
	private List<String> features = null;
	private Gender gender;

	List<Note> myNotes = new ArrayList<Note>();
	
	public StatRecord() {
		this(-1, Assets.ver, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1.0,-1,Gender.none,"n/a","n/a",new ArrayList<Advantage>(), new ArrayList<String>());
	}

	public StatRecord(int id, int ver, int ST, int DX, int IQ, int HT, int HP,
			int Will, int Per, int FP, int Dodge, int BM, int BL, double BS,
			int cost, Gender gender, String name, String job,
			List<Advantage> advantageRecord, List<String> features) {
		this.ST = ST;
		this.DX = DX;
		this.IQ = IQ;
		this.HT = HT;
		this.HP = HP;
		this.Will = Will;
		this.Per = Per;
		this.FP = FP;
		this.Dodge = Dodge;
		this.BM = BM;
		this.BL = BL;
		this.BS = BS;
		this.cost = cost;
		this.gender = gender;
		this.name = name;
		this.job = job;
		this.id = id;
		this.ver = ver;
		this.advantageRecord = advantageRecord;
		this.features = features;

		myNotes = new ArrayList<Note>();
	}

	// getters and setters
	public int getST() {
		return ST;
	}

	public void setST(int sT) {
		ST = sT;
	}

	public int getDX() {
		return DX;
	}

	public void setDX(int dX) {
		DX = dX;
	}

	public int getIQ() {
		return IQ;
	}

	public void setIQ(int iQ) {
		IQ = iQ;
	}

	public int getHT() {
		return HT;
	}

	public void setHT(int hT) {
		HT = hT;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getWill() {
		return Will;
	}

	public void setWill(int will) {
		Will = will;
	}

	public int getPer() {
		return Per;
	}

	public void setPer(int per) {
		Per = per;
	}

	public int getFP() {
		return FP;
	}

	public void setFP(int fP) {
		FP = fP;
	}

	public int getDodge() {
		return Dodge;
	}

	public void setDodge(int dodge) {
		Dodge = dodge;
	}

	public int getBM() {
		return BM;
	}

	public void setBM(int bM) {
		BM = bM;
	}

	public int getBL() {
		return BL;
	}

	public void setBL(int bL) {
		BL = bL;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public double getBS() {
		return BS;
	}

	public void setBS(double bS) {
		BS = bS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public List<Advantage> getAdvantageRecord() {
		return advantageRecord;
	}

	public void setAdvantageRecord(List<Advantage> ar) {
		advantageRecord = ar;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void addNotes(List<Note> notes) {
		for (Note n : notes) {
			addNote(n);
		}
	}

	public void addNote(Note n) {
		myNotes.add(n);
	}

	public void removeNote(Note n) {
		if (myNotes.contains(n)) {
			myNotes.remove(n);
		}
	}

	public void clearNotes() {
		myNotes = new ArrayList<Note>();
	}

	public Note getMostRecentNote() {
		Note mostRecent = null;
		if (ver >= 152) {
			if (!myNotes.isEmpty()) {
				for (Note n : myNotes) {
					if (mostRecent == null) {
						mostRecent = n;
					} else {
						if (mostRecent.getCreationTime() < n
								.getCreationTime()) {
							mostRecent = n;
						}
					}
				}
			}
		} else {
			mostRecent = new Note();
		}
		return mostRecent;
	}

	public List<String> getNotes(Sort sortType) {
		List<String> sortedNotes = new ArrayList<String>();
		if (ver >= 152) {
			if (sortType == Sort.recent01) {
				sortedNotes = Tools.sortOldestToNewest(myNotes);
			} else if (sortType == Sort.recent10) {
				sortedNotes = Tools.sortNewestToOldest(myNotes);
			} else {
				// no sorting
				for (Note n : myNotes) {
					sortedNotes.add(n.getNoteString());
				}
			}
		}

		return sortedNotes;
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
