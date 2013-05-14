package com.ameron32.rpgbuilder.npcrecord.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Names {

	// http://www.angelfire.com/rpg2/vortexshadow/names.html
	private String[] fName1 = { "Ama", "An", "Ang", "Ansr", "Ar", "Arì", "Arn", "Aza",
			"Bael", "Bes", "Cael", "Cal", "Cas", "Cla", "Cor", "Cy", "Dae",
			"Dho", "Dre", "Du", "Eil", "Eir", "El", "Er", "Ev", "Fera", "Fi",
			"Fir", "Fis", "Gael", "Gar", "Gil", "Ha", "Hu", "Ia", "Il", "Ja",
			"Jar", "Ka", "Kan", "Ker", "Keth", "Koeh", "Kor", "Ky", "La",
			"Laf", "Lam", "Lue", "Ly", "Mai", "Mal", "Mara", "My", "Na", "Nai",
			"Nim", "Nu", "Ny", "Py", "Raer", "Re", "Ren", "Rhy", "Ry", "Ru",
			"Rua", "Rum", "Rid", "Sae", "Seh", "Sel", "Sha", "She", "Si",
			"Sim", "Sol", "Sum", "Syl", "Ta", "Tahl", "Tha", "Tho", "Ther",
			"Thro", "Tia", "Tra", "Ty", "Try", "Uth", "Ver", "Vil", "Von",
			"Ya", "Za", "Zy" };
	private String[] fName2 = { "ae", "ael", "aer", "aias", "ah", "aith", "al", "ali",
			"am", "an", "ar", "ari", "aro", "as", "ath", "avel", "brar", "dar",
			"deth", "dre", "drim", "dul", "ean", "el", "emar", "en", "er",
			"ess", "evar", "fel", "hal", "har", "hel", "ian", "iat", "ik",
			"il", "im", "in", "ir", "is", "ith", "kash", "ki", "lan", "lam",
			"lar", "las", "lian", "lis", "lon", "lyn", "mah", "mil", "mus",
			"nal", "nes", "nin", "nis", "o", "or", "oth", "que", "quis", "ra",
			"ra", "rai", "ran", "reth", "ro", "ruil", "sal", "san", "sar",
			"sel", "sha", "spar", "tae", "tas", "ten", "thal", "thar", "ther",
			"thi", "thus", "ti", "tril", "ual", "uath", "us", "van", "var",
			"vain", "via", "vin", "wyn", "ya", "yr", "yth", "zair" };
	private String[] lName1 = { "Alean", "Alea", "Arabi", "Arkenea", "Auvrea",
			"Baequi", "Banni", "Cyred", "Dirth", "Dryear", "Dwin’", "Eyllis",
			"Eyther", "Freani", "Gysse", "Heasi", "Hlae", "Hunith", "Kennyr",
			"Kille", "Maern", "Melith", "Myrth", "Norre", "Orle", "Oussea",
			"Rilynn", "Teasen'", "Tyr", "Tyrnea" };
	private String[] lName2 = { "altin", "anea", "annia", "aear", "arnith", "atear",
			"athem", "dlues", "elrvis", "eplith", "ettln", "ghymn", "itryn",
			"lylth", "mitore", "nddare", "neldth", "rae", "raheal", "rretyn",
			"sithek", "thym", "tlarn", "tlithar", "tylar", "undlin", "urdrenn",
			"valsa", "virrea", "zea" };

	private List<String> fName1List = new ArrayList<String>();
	private List<String> fName2List = new ArrayList<String>();
	private List<String> lName1List = new ArrayList<String>();
	private List<String> lName2List = new ArrayList<String>();

	private Random r = new Random();

	public Names() {
		initialize();
	}

	private void initialize() {
		for (int x = 0; x < fName1.length; x++) {
			fName1List.add(fName1[x]);
		}
		for (int x = 0; x < fName2.length; x++) {
			fName2List.add(fName2[x]);
		}
		for (int x = 0; x < lName1.length; x++) {
			lName1List.add(lName1[x]);
		}
		for (int x = 0; x < lName2.length; x++) {
			lName2List.add(lName2[x]);
		}
	}

	private String generateName() {
		String generatedName = "";
		String nameFA = fName1List.get(r.nextInt(fName1List.size()));
		String nameFB = fName2List.get(r.nextInt(fName2List.size()));
		String nameLA = fName1List.get(r.nextInt(fName1List.size()));
		String nameLB = fName2List.get(r.nextInt(fName2List.size()));
		generatedName = nameFA + nameFB + " " + nameLA + nameLB;
		return generatedName;
	}

	public String getGeneratedName() {
		return generateName();
	}

}
