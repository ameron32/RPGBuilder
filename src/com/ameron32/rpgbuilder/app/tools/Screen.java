package com.ameron32.rpgbuilder.app.tools;

public class Screen {
	private Scr currentScreen;

	public Screen(int number) {
		setScreen(number);
	}
	
	public enum Scr {
		CharacterList, RecordCard, RecordCardEdit
	}

	public void setScreen(int number) {
		switch (number) {
		case 0:
			currentScreen = Scr.CharacterList;
			break;
		case 1:
			currentScreen = Scr.RecordCard;
			break;
		case 2:
			currentScreen = Scr.RecordCardEdit;
			break;
		}
	}

	public boolean isScreenShowing(Scr screen) {
		boolean returnMe = false;
		if (getScreenInt(screen) >= getScreenInt(currentScreen)) {
			returnMe = true;
		}
		return returnMe;
	}

	public int getScreenInt(Scr screen) {
		switch (screen) {
		case CharacterList:
			return 0;

		case RecordCard:
			return 1;

		case RecordCardEdit:
			return 2;

		default:
			return -1;
		}
	}

	public Scr getScreen() {
		return currentScreen;
	}

	public int nextScreen(Scr currentScreen) {
		// goes forward, does not loop
		switch (currentScreen) {
		case CharacterList:
			return 1;

		case RecordCard:
			return 2;

		case RecordCardEdit:
			return 2;

		default:
			return 0;

		}
	}

	public int prevScreen(Scr currentScreen) {
		// goes backward, does not loop
		switch (currentScreen) {
		case CharacterList:
			return 0;

		case RecordCard:
			return 0;

		case RecordCardEdit:
			return 1;

		default:
			return 0;

		}
	}
}
