package com.ameron32.rpgbuilder.app.tools;

import java.util.ArrayList;
import java.util.List;

public class Debugger {

	private static List<String> debuggerString = new ArrayList<String>();

	public static void addToDebuggerText(String s, boolean separator) {
		debuggerString.add(s + "\n");
		if (separator) {
			debuggerString.add("\n");
		}
	}
	
	public static void clearDebuggerText() {
		debuggerString = new ArrayList<String>();
	}
	
	public static List<String> getDebuggerText() {
		return debuggerString;
	}
	
}
