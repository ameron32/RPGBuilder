package com.ameron32.rpgbuilder.npcrecord.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jobs {

	private String[] jobs = { "Warrior", "Fighter", "Scholar", "Healer",
			"Rogue", "Hunter", "Ranger", "Paladin", "Teacher", "Herbologist",
			"Explorer", "Scout", "Sailor", "Weapon Trainer", "Musician",
			"Manservant(Maid)", "Beggar", "Jester", "Prince(ss)", "Wanderer",
			"Architect", "Slave", "Soldier", "Archer", "Archivist",
			"Stablekeeper", "Pigkeeper", "Ranchhand", "Miner", "Poet",
			"Adventurer", "Philosopher", "Hermit", "Scribe", "Cartographer",
			"Champion", "Singer", "Noble", "Barkeeper", "Blacksmith",
			"Weaponsmith", "Armorer", "Bowyer", "Bookseller", "Merchant",
			"Errand Runner", "Delivery Servant", "Leatherworker", "Tailor",
			"Jeweler", "Cobbler", "Fishmonger", "Farrier", "Carpenter",
			"Mason", "Thief", "Scoundrel", "Guard", "Knight(Lady)", "Vassal",
			"Farmer", "Lord" };

	// private List<String> jobsList = new ArrayList<String>();
	List<Job> jobList = new ArrayList<Job>();
	List<Job> jobListMale = new ArrayList<Job>();
	List<Job> jobListFemale = new ArrayList<Job>();

	private Random r = new Random();

	public Jobs() {
		initialize();
	}

	int total, totalMale, totalFemale;

	private void initialize() {
		total = totalMale = totalFemale = 0;
		jobList.add(new Job("Musician", Group.entertainment, 354, true, false));
		jobList.add(new Job("Poet", Group.entertainment, 354, true, false));
		jobList.add(new Job("Singer", Group.entertainment, 354, true, false));
		jobList.add(new Job("Thief", Group.adventurers, 354, true, false));
		jobList.add(new Job("Warrior", Group.adventurers, 354, true, false));
		jobList.add(new Job("Fighter", Group.adventurers, 354, true, false));
		jobList.add(new Job("Rogue", Group.adventurers, 354, true, false));
		jobList.add(new Job("Hunter", Group.adventurers, 354, true, false));
		jobList.add(new Job("Ranger", Group.adventurers, 354, true, false));
		jobList.add(new Job("Wanderer", Group.adventurers, 354, true, false));
		jobList.add(new Job("Adventurer", Group.adventurers, 354, true, false));
		jobList.add(new Job("Stablekeeper", Group.gatherer, 500, true, false));
		jobList.add(new Job("Pigkeeper", Group.gatherer, 1000, true, false));
		jobList.add(new Job("Ranchhand", Group.gatherer, 1250, true, false));
		jobList.add(new Job("Miner", Group.gatherer, 1000, true, false));
		jobList.add(new Job("Fisherman", Group.gatherer, 1666, true, false));
		jobList.add(new Job("Farmer", Group.gatherer, 1666, true, false));
		jobList.add(new Job("Teacher", Group.knowledge, 100, true, false));
		jobList.add(new Job("Philosopher", Group.knowledge, 25, true, false));
		jobList.add(new Job("Scribe", Group.knowledge, 25, true, false));
		jobList.add(new Job("Cartographer", Group.knowledge, 25, true, false));
		jobList.add(new Job("Explorer", Group.merchant, 50, true, false));
		jobList.add(new Job("Sailor", Group.merchant, 416, true, false));
		jobList.add(new Job("Silversmith", Group.merchant, 118, true, false));
		jobList.add(new Job("Blacksmith", Group.merchant, 236, true, false));
		jobList.add(new Job("Weaponsmith", Group.merchant, 236, true, false));
		jobList.add(new Job("Armorer", Group.merchant, 236, true, false));
		jobList.add(new Job("Bowyer", Group.merchant, 236, true, false));
		jobList.add(new Job("Bookseller", Group.merchant, 50, true, false));
		jobList.add(new Job("Merchant", Group.merchant, 400, true, false));
		jobList.add(new Job("Leatherworker", Group.merchant, 354, true, false));
		jobList.add(new Job("Jeweler", Group.merchant, 70, true, false));
		jobList.add(new Job("Cobbler", Group.merchant, 354, true, false));
		jobList.add(new Job("Fishmonger", Group.merchant, 333, true, false));
		jobList.add(new Job("Scout", Group.military, 35, true, false));
		jobList.add(new Job("Soldier", Group.military, 708, true, false));
		jobList.add(new Job("Archer", Group.military, 236, true, false));
		jobList.add(new Job("Guard", Group.military, 236, true, false));
		jobList.add(new Job("Slave", Group.poor, 250, true, false));
		jobList.add(new Job("Manservant", Group.poor, 128, true, false));
		jobList.add(new Job("Beggar", Group.poor, 150, true, false));
		jobList.add(new Job("Hermit", Group.poor, 5, true, false));
		jobList.add(new Job("Scoundrel", Group.poor, 25, true, false));
		jobList.add(new Job("Herbologist", Group.services, 75, true, false));
		jobList.add(new Job("Barkeeper", Group.services, 354, true, false));
		jobList.add(new Job("Errand Runner", Group.services, 178, true, false));
		jobList.add(new Job("Delivery Servant", Group.services, 178, true,
				false));
		jobList.add(new Job("Tailor", Group.services, 708, true, false));
		jobList.add(new Job("Farrier", Group.services, 50, true, false));
		jobList.add(new Job("Carpenter", Group.services, 708, true, false));
		jobList.add(new Job("Mason", Group.services, 566, true, false));
		jobList.add(new Job("Alchemist", Group.services, 75, true, false));
		jobList.add(new Job("Baker", Group.services, 500, true, false));
		jobList.add(new Job("Butcher", Group.services, 500, true, false));
		jobList.add(new Job("Healer", Group.special, 125, true, false));
		jobList.add(new Job("Weapon Trainer", Group.special, 50, true, false));
		jobList.add(new Job("Jester", Group.court, 12, true, false));
		jobList.add(new Job("Prince", Group.court, 124, true, false));
		jobList.add(new Job("Noble", Group.court, 32, true, false));
		jobList.add(new Job("Knight", Group.court, 80, true, false));
		jobList.add(new Job("Lord", Group.court, 4, true, false));
		jobList.add(new Job("Scholar", Group.knowledge, 360, true, false));
		jobList.add(new Job("Archivist", Group.knowledge, 120, true, false));
		jobList.add(new Job("Paladin", Group.military, 100, true, false));
		jobList.add(new Job("Architect", Group.special, 50, true, false));
		jobList.add(new Job("Champion", Group.special, 14, true, false));
		jobList.add(new Job("Musician", Group.entertainment, 354, false, true));
		jobList.add(new Job("Poet", Group.entertainment, 354, false, true));
		jobList.add(new Job("Singer", Group.entertainment, 354, false, true));
		jobList.add(new Job("Thief", Group.adventurers, 177, false, true));
		jobList.add(new Job("Warrior", Group.adventurers, 177, false, true));
		jobList.add(new Job("Fighter", Group.adventurers, 177, false, true));
		jobList.add(new Job("Rogue", Group.adventurers, 177, false, true));
		jobList.add(new Job("Hunter", Group.adventurers, 177, false, true));
		jobList.add(new Job("Ranger", Group.adventurers, 177, false, true));
		jobList.add(new Job("Wanderer", Group.adventurers, 177, false, true));
		jobList.add(new Job("Adventurer", Group.adventurers, 177, false, true));
		jobList.add(new Job("Stablekeeper", Group.gatherer, 125, false, true));
		jobList.add(new Job("Pigkeeper", Group.gatherer, 2000, false, true));
		jobList.add(new Job("Ranchhand", Group.gatherer, 625, false, true));
		jobList.add(new Job("Miner", Group.gatherer, 250, false, true));
		jobList.add(new Job("Fisherman's wife", Group.gatherer, 1666, false,
				true));
		jobList.add(new Job("Farmer's wife", Group.gatherer, 1666, false, true));
		jobList.add(new Job("Teacher", Group.knowledge, 100, false, true));
		jobList.add(new Job("Philosopher's Wife", Group.knowledge, 18, false,
				true));
		jobList.add(new Job("Scribe's Wife", Group.knowledge, 18, false, true));
		jobList.add(new Job("Cartographer's Wife", Group.knowledge, 18, false,
				true));
		jobList.add(new Job("Explorer's Wife", Group.merchant, 37, false, true));
		jobList.add(new Job("Sailor's Wife", Group.merchant, 312, false, true));
		jobList.add(new Job("Silversmith's Wife", Group.merchant, 88, false,
				true));
		jobList.add(new Job("Blacksmith's Wife", Group.merchant, 177, false,
				true));
		jobList.add(new Job("Weaponsmith's Wife", Group.merchant, 177, false,
				true));
		jobList.add(new Job("Armorer's Wife", Group.merchant, 177, false, true));
		jobList.add(new Job("Bowyer's Wife", Group.merchant, 177, false, true));
		jobList.add(new Job("Bookseller's Wife", Group.merchant, 37, false,
				true));
		jobList.add(new Job("Merchant's Wife", Group.merchant, 300, false, true));
		jobList.add(new Job("Leatherworker's Wife", Group.merchant, 265, false,
				true));
		jobList.add(new Job("Jeweler's Wife", Group.merchant, 53, false, true));
		jobList.add(new Job("Cobbler's wife", Group.merchant, 354, false, true));
		jobList.add(new Job("Fishmonger's wife", Group.merchant, 333, false,
				true));
		jobList.add(new Job("Scout", Group.military, 7, false, true));
		jobList.add(new Job("Soldier", Group.military, 177, false, true));
		jobList.add(new Job("Archer", Group.military, 236, false, true));
		jobList.add(new Job("Guard", Group.military, 283, false, true));
		jobList.add(new Job("Slave", Group.poor, 250, false, true));
		jobList.add(new Job("Maidservant", Group.poor, 128, false, true));
		jobList.add(new Job("Beggar", Group.poor, 150, false, true));
		jobList.add(new Job("Hag", Group.poor, 20, false, true));
		jobList.add(new Job("Herbologist", Group.poor, 225, false, true));
		jobList.add(new Job("Barwench", Group.services, 1062, false, true));
		jobList.add(new Job("Errand Runner", Group.services, 178, false, true));
		jobList.add(new Job("Delivery Servant", Group.services, 178, false,
				true));
		jobList.add(new Job("Tailor", Group.services, 1062, false, true));
		jobList.add(new Job("Farrier", Group.services, 25, false, true));
		jobList.add(new Job("Carpenter's Wife", Group.services, 708, false,
				true));
		jobList.add(new Job("Mason's Wife", Group.services, 566, false, true));
		jobList.add(new Job("Alchemist's Wife", Group.services, 75, false, true));
		jobList.add(new Job("Baker", Group.services, 500, false, true));
		jobList.add(new Job("Butcher", Group.services, 500, false, true));
		jobList.add(new Job("Healer", Group.services, 125, false, true));
		jobList.add(new Job("Weapon Trainer's Wife", Group.special, 50, false,
				true));
		jobList.add(new Job("Jester", Group.special, 6, false, true));
		jobList.add(new Job("Princess", Group.court, 124, false, true));
		jobList.add(new Job("Lady", Group.court, 116, false, true));
		jobList.add(new Job("Scholar's Wife", Group.court, 360, false, true));
		jobList.add(new Job("Archivist's Wife", Group.court, 120, false, true));
		jobList.add(new Job("Paladin's Wife", Group.court, 100, false, true));
		jobList.add(new Job("Architect's Wife", Group.knowledge, 50, false,
				true));
		jobList.add(new Job("Champion's Wife", Group.knowledge, 14, false, true));

		for (Job j : jobList) {
			total += j.getFrequency();
			if (j.isMale()) {
				jobListMale.add(j);
				totalMale += j.getFrequency();
			} else if (j.isFemale()) {
				jobListFemale.add(j);
				totalFemale += j.getFrequency();
			}
		}
	}

	private String generateJob(Gender g) {
		String generatedJob = "";
		int x = 0;
		Job j = jobList.get(0);
		if (g == null) {
			int thisJob = r.nextInt(total);
			while (thisJob > 0) {
				j = jobList.get(x);
				thisJob -= j.getFrequency();
				x++;
			}
		} else if (g == Gender.male) {
			int thisJob = r.nextInt(totalMale);
			while (thisJob > 0) {
				j = jobListMale.get(x);
				thisJob -= j.getFrequency();
				x++;
			}
		} else if (g == Gender.female) {
			int thisJob = r.nextInt(totalFemale);
			while (thisJob > 0) {
				j = jobListFemale.get(x);
				thisJob -= j.getFrequency();
				x++;
			}
		}
		generatedJob = j.getName();
		return generatedJob;
	}

	public String getGeneratedJob() {
		return generateJob(null);
	}

	public String getGeneratedJobFor(Gender g) {
		return generateJob(g);
	}

	public String getGeneratedJobFor(Gender g, Group group) {
		String toReturn = "";
		Job toReturnJob = null;
		List<Job> iterate = new ArrayList<Job>();
		List<Job> chooseFromList = new ArrayList<Job>();
		if (g == Gender.none) {

		} else if (g == Gender.male) {
			iterate = jobListMale;
		} else if (g == Gender.female) {
			iterate = jobListFemale;
		}
		for (Job j : iterate) {
			if (j.getGroup() == group) {
				chooseFromList.add(j);
			}
		}
		int specialListTotal = 0;
		for (Job j : chooseFromList) {
			specialListTotal += j.getFrequency();
		}
		int thisJob = r.nextInt(specialListTotal);
		int x = 0;
		while (thisJob > 0) {
			toReturnJob = chooseFromList.get(x);
			thisJob -= toReturnJob.getFrequency();
			x++;
		}
		toReturn = toReturnJob.getName();
		return toReturn;
	}

}
