package com.ameron32.rpgbuilder.npcrecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.advantageaddon.CsvWriter;
import com.ameron32.rpgbuilder.app.tools.Tools.Sort;

public class NPCRecordExporter {
	CsvWriter csvOutput;
	NPCRecord nr;

	String[] headers = {"npcId", "npcVersion", "npcLastModified",
			"id", "ver", "st", "dx", "iq", "ht", "hp", "will", "per", "fp",
			"dodge","bm","bl","bs","cost",
			"name","job",
			"advs","features",
			"gender",
			"notes"	
		};

	public NPCRecordExporter() {

	}

	public boolean writeAllNPCRecords(List<NPCRecord> nrs, String path,
			Boolean replaceYN) {

		String outputFile = path;

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(outputFile).exists();

		try {
			// use FileWriter constructor that specifies open for appending
			csvOutput = new CsvWriter(
					new FileWriter(outputFile + ".csv", true), ',');

			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists || replaceYN) {
				for (String h : headers) {
					csvOutput.write(h);
				}
				csvOutput.endRecord();
			}

			// else assume that the file already has the correct header line

			// write out a few records
			for (NPCRecord n : nrs) {
				nr = n;
				write(n.getStatRecord().getVer());
			}

			csvOutput.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void write(int ver) throws IOException {
		switch (ver) {
		
		case 150:
		case 151:
		case 152:
		case 153:
			write0150();
			break;
		}
	}

	private void write0150() throws IOException {
		/*
		 * csvOutput.write(nr.get());
		 * csvOutput.write(String.valueOf(nr.getId()));
		 * csvOutput.write(String.valueOf(nr.getGoldValue()));
		 * csvOutput.write(nr.getType());
		 * csvOutput.write(String.valueOf(nr.getDamage()));
		 * csvOutput.write(String.valueOf(nr.getResistance()));
		 */
		csvOutput.write(Integer.toString(nr.getId()));
		csvOutput.write(Integer.toString(nr.getVer()));
		csvOutput.write(Long.toString(nr.getLastModified()));
		StatRecord sr = nr.getStatRecord();
		csvOutput.write(Integer.toString(sr.getId()));
		csvOutput.write(Integer.toString(sr.getVer()));
		csvOutput.write(Integer.toString(sr.getST()));
		csvOutput.write(Integer.toString(sr.getDX()));
		csvOutput.write(Integer.toString(sr.getIQ()));
		csvOutput.write(Integer.toString(sr.getHT()));
		csvOutput.write(Integer.toString(sr.getHP()));
		csvOutput.write(Integer.toString(sr.getWill()));
		csvOutput.write(Integer.toString(sr.getPer()));
		csvOutput.write(Integer.toString(sr.getFP()));
		csvOutput.write(Integer.toString(sr.getDodge()));
		csvOutput.write(Integer.toString(sr.getBM()));
		csvOutput.write(Integer.toString(sr.getBL()));
		csvOutput.write(Double.toString(sr.getBS()));
		csvOutput.write(Integer.toString(sr.getCost()));
		csvOutput.write(sr.getName());
		csvOutput.write(sr.getJob());
		StringBuilder sb = new StringBuilder();
		for (Advantage a : sr.getAdvantageRecord()) {
			sb.append(a.getId() + ":" + a.getNameString());
			sb.append("|");
		}
		csvOutput.write(sb.toString());
		sb = new StringBuilder();
		for (String f : sr.getFeatures()) {
			sb.append(f);
			sb.append("|");
		}
		csvOutput.write(sb.toString());
		csvOutput.write(sr.getGender().name());
		sb = new StringBuilder();
		for (String n : sr.getNotes(Sort.recent01)) {
			sb.append(n);
			sb.append("|");
		}
		csvOutput.write(sb.toString());

		csvOutput.endRecord();
	}
}
