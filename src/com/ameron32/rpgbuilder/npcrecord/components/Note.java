package com.ameron32.rpgbuilder.npcrecord.components;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
	private static final long serialVersionUID = 5515465605915770066L;
	
	private int id;
	private NoteType nt;
	private String noteString;
	private Long creationTime;
		
	public Note() {
		create(-1, NoteType.undefined, "");
	}
	
	public Note (int id, NoteType nt, String noteString) {
		create(id, nt, noteString);
	}
	
	private void create(int id, NoteType nt, String noteString) {
		this.id = id;
		this.nt = nt;
		this.noteString = noteString;
		
		// add a timestamp
		Date creationTimeInDateForm = new Date();
		creationTime = creationTimeInDateForm.getTime();
	}

	public String getNoteString() {
		return noteString;
	}

	public void setNoteString(String noteString) {
		this.noteString = noteString;
	}

	public int getId() {
		return id;
	}

	public NoteType getNt() {
		return nt;
	}

	public Long getCreationTime() {
		return creationTime;
	}

}
