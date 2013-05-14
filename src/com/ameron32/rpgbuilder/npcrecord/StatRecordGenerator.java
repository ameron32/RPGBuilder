package com.ameron32.rpgbuilder.npcrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ameron32.rpgbuilder.Assets;
import com.ameron32.rpgbuilder.advantageaddon.AdvTools.AdvType;
import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.app.tools.Tools;
import com.ameron32.rpgbuilder.npcrecord.components.Gender;
import com.ameron32.rpgbuilder.npcrecord.components.Jobs;
import com.ameron32.rpgbuilder.npcrecord.components.MoreFeatures;
import com.ameron32.rpgbuilder.npcrecord.components.Names;
import com.ameron32.rpgbuilder.npcrecord.components.MoreFeatures.FeatureType;

public class StatRecordGenerator {

	private int ST, DX, IQ, HT;
	private int HP, Will, Per, FP, Dodge, BM, BL;
	private double BS;
	private int cost;
	private String name, job;
	private Gender gender;

	private StatRecord statRecord;
	private List<Advantage> advantageRecord;
	private List<String> features;

	public void setStatRecord(StatRecord sr) {
		statRecord = sr;
	}

	public StatRecord getStatRecord() {
		return statRecord;
	}

	public StatRecordGenerator() {
		create(-1, -1, -1, -1, null, null, null, null, null);
	}
	
	public StatRecordGenerator(int ST, int DX, int IQ, int HT, Gender gender, String name,
			String job, List<Advantage> advantages, List<String> features) {
		create(ST, DX, IQ, HT, gender, name, job, advantages, features);
	}
	
	private void create(int ST, int DX, int IQ, int HT, Gender gender, String name,
			String job, List<Advantage> advantages, List<String> features) {
		initialize(ST, DX, IQ, HT, gender, name, job, advantages, features);
		generateStats();
		cost = calculatePointCost();
		statRecord = new StatRecord(Tools.randomId(), Assets.ver, this.ST, this.DX, this.IQ, this.HT,
				HP, Will, Per, FP, Dodge, BM, BL, BS, cost, this.gender, this.name, this.job, advantageRecord, this.features);
	}

	private void initialize(int ST, int DX, int IQ, int HT, Gender gender, String name,
			String job, List<Advantage> advantages, List<String> features) {
		if (ST > -1) {
			this.ST = ST;
		} else {
			this.ST = Tools.normalizedRandom();
		}
		if (DX > -1) {
			this.DX = DX;
		} else {
			this.DX = Tools.normalizedRandom();
		}
		if (IQ > -1) {
			this.IQ = IQ;
		} else {
			this.IQ = Tools.normalizedRandom();
		}
		if (HT > -1) {
			this.HT = HT;
		} else {
			this.HT = Tools.normalizedRandom();
		}
		if (gender == null) {
			this.gender = generateGender();
		} else {
			this.gender = gender;
		}
		if (name == null) {
			this.name = generateName();
		} else {
			this.name = name;
		}
		if (job == null) {
			this.job = generateJob(this.gender);
		} else {
			this.job = job;
		}
		if (advantages == null) {
			this.advantageRecord = generateAdvantages();
		} else {
			this.advantageRecord = advantages;
		}
		if (features == null) {
			this.features = generateFeatures();
		} else {
			this.features = features;
		}
	}

	private void generateStats() {
		calculateSecondaryStats();
	}

	private void calculateSecondaryStats() {
		HP = ST;
		Will = IQ;
		Per = IQ;
		FP = HP;
		BS = (HT + DX) / 4.0;
		Dodge = (int) Math.round(Math.floor(BS) + 3);
		BM = (int) Math.round(Math.floor(BS));
		BL = (ST * ST) / 5;
	}

	private int calculatePointCost() {
		int c = determinePointCost();
		return c;
	}

	private int determinePointCost() {
		int totalCost = 0;
		int[] stat10 = { ST, HT };
		int[] stat20 = { DX, IQ };

		// ST, HT
		for (int x = 0; x < stat10.length; x++) {
			totalCost += oneCost(stat10[x], 10);
		}

		// DX, IQ
		for (int x = 0; x < stat20.length; x++) {
			totalCost += oneCost(stat20[x], 20);
		}

		for (Advantage a : advantageRecord) {
			totalCost += a.getCalcCost();
		}

		return totalCost;
	}

	private int oneCost(int stat, int pointCost) {
		if (stat > 0 && stat < 21) {
			int cost = (stat - 10) * pointCost;
			return cost;
		} else {
			return -99999;
		}
	}

	public Gender generateGender() {
		Gender g = null;
		if (new Random().nextBoolean()) {
			g = Gender.male;
		} else {
			g = Gender.female;
		}
		return g;
	}
	
	public String generateName() {
		Names n = new Names();
		return n.getGeneratedName();
	}

	public String generateJob(Gender g) {
		Jobs j = new Jobs();
		return j.getGeneratedJobFor(g);
	}

	public List<Advantage> generateAdvantages() {
		return generateAdvantages(-1, -1, -1, -1);
	}
	
	public List<Advantage> generateAdvantages(int noOfAdvs, int noOfDisadvs,
			int noOfPerks, int noOfQuirks) {
		List<Advantage> generatedList = new ArrayList<Advantage>();
	
		int noOfAdvsi;
		int noOfDisadvsi;
		int noOfPerksi;
		int noOfQuirksi;
		if (noOfAdvs == -1) {
			noOfAdvsi = Tools.normalizedRandomOf(2) + 1;
		} else {
			noOfAdvsi = noOfAdvs;
		}
		if (noOfAdvs == -1) {
			noOfDisadvsi = Tools.normalizedRandomOf(2) + 1;
		} else {
			noOfDisadvsi = noOfDisadvs;
		}
		if (noOfAdvs == -1) {
			noOfPerksi = Tools.normalizedRandomOf(2) + 1;
		} else {
			noOfPerksi = noOfPerks;
		}
		if (noOfAdvs == -1) {
			noOfQuirksi = Tools.normalizedRandomOf(2) + 1;
		} else {
			noOfQuirksi = noOfQuirks;
		}
	
		generatedList.addAll(addAdvantages(noOfAdvsi, AdvType.advantage, generatedList));
		generatedList.addAll(addAdvantages(noOfDisadvsi, AdvType.disadvantage, generatedList));
		generatedList.addAll(addAdvantages(noOfPerksi, AdvType.perk, generatedList));
		generatedList.addAll(addAdvantages(noOfQuirksi, AdvType.quirk, generatedList));
	
		return generatedList;
	}

	public List<String> generateFeatures() {
		// TODO features aren't gender specific yet
		MoreFeatures mf = new MoreFeatures();
		return mf.getGeneratedFeaturesList(gender);
	}
	
	public String generateFeature(FeatureType ft) {
		MoreFeatures mf = new MoreFeatures();
		return mf.generateOneFeature(ft);
	}

	private List<Advantage> addAdvantages(int numberOf, AdvType aType,
			List<Advantage> generatedList) {
		List<Advantage> as = new ArrayList<Advantage>();
		for (int i = 0; i < numberOf; i++) {
			boolean done = false;
			while (!done) {
				done = addNoDups(generateOne(aType), generatedList);
			}
		}
		return as;
	}

	private boolean addNoDups(Advantage a, List<Advantage> advs) {
		boolean added;
		if (!advs.contains(a)) {
			advs.add(a);
			added = true;
		} else {
			added = false;
		}
		return added;
	}

	private List<Advantage> filterOnly(AdvType at) {
		List<Advantage> filteredList = new ArrayList<Advantage>();
		switch (at) {
		case advantage:
			for (Advantage a : Assets.advantages) {
				boolean addMe = false;
				if (Assets.noForbidden) {
					if (a.getIsForbidden()) {
						addMe = false;
					} else {
						addMe = true;
					}
				} else {
					addMe = true;
				}
				if (addMe) {
					if (a.getAorD().equalsIgnoreCase("A")
							&& a.getCalcCost() != 1) {
						filteredList.add(a);
					}
				}
			}
			break;
		case disadvantage:
			for (Advantage d : Assets.advantages) {
				boolean addMe = false;
				if (Assets.noForbidden) {
					if (d.getIsForbidden()) {
						addMe = false;
					} else {
						addMe = true;
					}
				} else {
					addMe = true;
				}
				if (addMe) {
					if (d.getAorD().equalsIgnoreCase("D")
							&& d.getCalcCost() != -1) {
						filteredList.add(d);
					}
				}
			}
			break;
		// TODO fix perk/quirk in csv file
		case perk:
			for (Advantage p : Assets.advantages) {
				boolean addMe = false;
				if (Assets.noForbidden) {
					if (p.getIsForbidden()) {
						addMe = false;
					} else {
						addMe = true;
					}
				} else {
					addMe = true;
				}
				if (addMe) {
					if (p.getAorD().equalsIgnoreCase("A")
							&& p.getCalcCost() == 1) {
						filteredList.add(p);
					}
				}
			}
			break;
		case quirk:
			for (Advantage q : Assets.advantages) {
				boolean addMe = false;
				if (Assets.noForbidden) {
					if (q.getIsForbidden()) {
						addMe = false;
					} else {
						addMe = true;
					}
				} else {
					addMe = true;
				}
				if (addMe) {
					if (q.getAorD().equalsIgnoreCase("D")
							&& q.getCalcCost() == -1) {
						filteredList.add(q);
					}
				}
			}
			break;
		}
		return filteredList;
	}

	public Advantage generateOne(AdvType at) {
		Random r = new Random();
		Advantage returnAdvantage = null;
		switch (at) {
		case advantage:
			List<Advantage> advsOnly = filterOnly(AdvType.advantage);
			int totalAdvs = advsOnly.size();
			returnAdvantage = advsOnly.get(r.nextInt(totalAdvs));
			break;
		case disadvantage:
			List<Advantage> disadvsOnly = filterOnly(AdvType.disadvantage);
			int totalDisAdvs = disadvsOnly.size();
			returnAdvantage = disadvsOnly.get(r.nextInt(totalDisAdvs));
			break;
		case perk:
			List<Advantage> perksOnly = filterOnly(AdvType.perk);
			int totalPerks = perksOnly.size();
			returnAdvantage = perksOnly.get(r.nextInt(totalPerks));
			break;
		case quirk:
			List<Advantage> quirksOnly = filterOnly(AdvType.quirk);
			int totalQuirks = quirksOnly.size();
			returnAdvantage = quirksOnly.get(r.nextInt(totalQuirks));
			break;
		}
		// int totalAll = Assets.advantages.size();
		return returnAdvantage;
	}
}
