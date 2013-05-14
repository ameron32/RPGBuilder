package com.ameron32.rpgbuilder.app.tools;

public class ViewTag extends Object {

	int id;
	String name;
	String type;
	String more;

	public ViewTag(int id, String name, String type, String more) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.more = more;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

}
