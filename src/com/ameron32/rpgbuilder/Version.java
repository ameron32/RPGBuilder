package com.ameron32.rpgbuilder;

public class Version {

	int number;
	String title;
	String notes;
	
	public Version (int number, String title, String notes) {
		this.number = number;
		this.title = title;
		this.notes = notes;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
