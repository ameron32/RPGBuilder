package com.ameron32.rpgbuilder.advantageaddon;



public class AdvTools {

	public static AdvType getAdvTypeForAdvantage(Advantage adv) {
		AdvType type;
		if (adv.getAorD().equalsIgnoreCase("A")
				&& adv.getCalcCost() == 1) {
			type = AdvType.perk;
		} else if (adv.getAorD().equalsIgnoreCase("D")
				&& adv.getCalcCost() == -1) {
			type = AdvType.quirk;
		} else if (adv.getAorD().equalsIgnoreCase("A")) {
			type = AdvType.advantage;
		} else if (adv.getAorD().equalsIgnoreCase("D")) {
			type = AdvType.disadvantage;
		} else {
			type = null;
		}
		return type;
	}
	
	public static enum AdvType {
		advantage, disadvantage, perk, quirk
	}
	
}
