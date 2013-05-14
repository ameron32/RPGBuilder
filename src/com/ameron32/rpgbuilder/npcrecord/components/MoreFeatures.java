package com.ameron32.rpgbuilder.npcrecord.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreFeatures {

	private String[] buildFeatures = { "plump", "stocky", "overweight", "fat",
			"slim", "trim", "skinny", "buff", "well-built" };
	private String[] heightFeatures = { "shorter", "taller", "petite",
			"average height" };
	private String[] complexionFeatures = { "dark-skinned", "light-skinned",
			"fair-skinned", "olive-skinned", "pale-skinned", "tanned",
			"pimply", "freckled", "spotty skin" };
	private String[] hairFeatures = { "long hair", "short hair", "curly hair",
			"frizzy hair", "straight hair", "totally bald", "balding hair",
			"receding hair", "thick hair", "wild hair", "thinning hair",
			"fair hair" };
	private String[] hairColorFeatures = { "blond hair", "red hair",
			"brown hair", "black hair", "grey hair", "white hair" };
	private String[] scentFeatures = { "smelly", "perfumed",
			"naturally fragrant", "body odor" };
	private String[] attractivenessFeatures = { "pretty", "somewhat-ugly",
			"cute", "funny-looking", "well-fitting clothing" };
	private String[] ageFeatures = { "younger", "older", "average-aged" };
	private String[] imperfectionFeature = { "facial scar",
			"large facial birthmark", "body tattoo",
			"small noticable birthmark", "earring(s)", "face paint",
			"twitchy face", "arm scar", "missing finger(s)",
			"nose or face ring", "word whiskers", "unique item",
			"missing eye/nose/teeth" };

	private List<String> buildFeaturesList = new ArrayList<String>();
	private List<String> heightFeaturesList = new ArrayList<String>();
	private List<String> complexionFeaturesList = new ArrayList<String>();
	private List<String> hairFeaturesList = new ArrayList<String>();
	private List<String> hairColorFeaturesList = new ArrayList<String>();
	private List<String> scentFeaturesList = new ArrayList<String>();
	private List<String> attractivenessFeaturesList = new ArrayList<String>();
	private List<String> ageFeaturesList = new ArrayList<String>();
	private List<String> imperfectionFeatureList = new ArrayList<String>();

	private Random r = new Random();

	public MoreFeatures() {
		initialize();
	}

	private void initialize() {
		addToList(buildFeatures, buildFeaturesList);
		addToList(heightFeatures, heightFeaturesList);
		addToList(complexionFeatures, complexionFeaturesList);
		addToList(hairFeatures, hairFeaturesList);
		addToList(hairColorFeatures, hairColorFeaturesList);
		addToList(scentFeatures, scentFeaturesList);
		addToList(attractivenessFeatures, attractivenessFeaturesList);
		addToList(ageFeatures, ageFeaturesList);
		addToList(imperfectionFeature, imperfectionFeatureList);
	}

	private void addToList(String[] array, List<String> stringlist) {
		for (int x = 0; x < array.length; x++) {
			stringlist.add(array[x]);
		}
	}

	private List<String> generateFeatures() {
		// generate the random features from all features
		List<String> generatedFeatures = new ArrayList<String>();
		List<Integer> chosenLists = new ArrayList<Integer>();
		// feature chooser loop
		for (int x = 0; x < (r.nextInt(4) + 1); x++) {
			boolean newListFound = false;
			int randomList = 0;
			while (!newListFound) {
				randomList = r.nextInt(9);
				if (!chosenLists.contains(randomList)) {
					newListFound = true;
					chosenLists.add(randomList);
				}
			}
			FeatureType randomFeatureType = null;
			switch (randomList) {
			case 0:
				randomFeatureType = FeatureType.build;
				break;
			case 1:
				randomFeatureType = FeatureType.height;
				break;
			case 2:
				randomFeatureType = FeatureType.complexion;
				break;
			case 3:
				randomFeatureType = FeatureType.hair;
				break;
			case 4:
				randomFeatureType = FeatureType.haircolor;
				break;
			case 5:
				randomFeatureType = FeatureType.scent;
				break;
			case 6:
				randomFeatureType = FeatureType.attractiveness;
				break;
			case 7:
				randomFeatureType = FeatureType.age;
				break;
			case 8:
				randomFeatureType = FeatureType.imperfection;
				break;
			}
			String s = generateOneFeature(randomFeatureType);
			generatedFeatures.add(s);
		}
		return generatedFeatures;
	}

	public String generateOneFeature(FeatureType ft) {
		String generatedFeature = "";
		switch (ft) {
		case build:
			generatedFeature = getRandomFeature(buildFeaturesList);
			break;
		case height:
			generatedFeature = getRandomFeature(heightFeaturesList);
			break;
		case complexion:
			generatedFeature = getRandomFeature(complexionFeaturesList);
			break;
		case hair:
			generatedFeature = getRandomFeature(hairFeaturesList);
			break;
		case haircolor:
			generatedFeature = getRandomFeature(hairColorFeaturesList);
			break;
		case scent:
			generatedFeature = getRandomFeature(scentFeaturesList);
			break;
		case attractiveness:
			generatedFeature = getRandomFeature(attractivenessFeaturesList);
			break;
		case age:
			generatedFeature = getRandomFeature(ageFeaturesList);
			break;
		case imperfection:
			generatedFeature = getRandomFeature(imperfectionFeatureList);
			break;
		}
		return generatedFeature;
	}

	private String getRandomFeature(List<String> featureList) {
		String randomFeature = "";
		randomFeature = featureList.get(r.nextInt(featureList.size()));
		return randomFeature;
	}

	public List<String> getGeneratedFeaturesList(Gender g) {
		return generateFeatures();
	}

	public FeatureType findFeatureTypeFromString(String feature) {
		FeatureType returnMe = null;
		if (feature != null && feature != "") {
			if (isFoundInList(feature, buildFeaturesList)) {
				returnMe = FeatureType.build;
			} else if (isFoundInList(feature, heightFeaturesList)) {
				returnMe = FeatureType.height;
			} else if (isFoundInList(feature, complexionFeaturesList)) {
				returnMe = FeatureType.complexion;
			} else if (isFoundInList(feature, hairFeaturesList)) {
				returnMe = FeatureType.hair;
			} else if (isFoundInList(feature, hairColorFeaturesList)) {
				returnMe = FeatureType.haircolor;
			} else if (isFoundInList(feature, scentFeaturesList)) {
				returnMe = FeatureType.scent;
			} else if (isFoundInList(feature, attractivenessFeaturesList)) {
				returnMe = FeatureType.attractiveness;
			} else if (isFoundInList(feature, ageFeaturesList)) {
				returnMe = FeatureType.age;
			} else if (isFoundInList(feature, imperfectionFeatureList)) {
				returnMe = FeatureType.imperfection;
			} else {
				returnMe = FeatureType.other;
			}
		}
		return returnMe;
	}
	
	public boolean isFoundInList(String findMe, List<String> featureList) {
		boolean returnMe = false;
		for (String s : featureList) {
			if (s.equalsIgnoreCase(findMe)) {
				returnMe = true;
			}
		}
		return returnMe;
	}

	public enum FeatureType {
		build, height, complexion, hair, haircolor, scent, attractiveness, age, imperfection, other
	}

}
