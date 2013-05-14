package com.ameron32.rpgbuilder.npcrecord.components;

public class Job {
	String name;
	Group group;
	int frequency;
	boolean male;
	boolean female;

	public Job(String name, Group group, int frequency, boolean male,
			boolean female) {
		this.name = name;
		this.group = group;
		this.frequency = frequency;
		this.male = male;
		this.female = female;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public boolean isFemale() {
		return female;
	}

	public void setFemale(boolean female) {
		this.female = female;
	}
}
