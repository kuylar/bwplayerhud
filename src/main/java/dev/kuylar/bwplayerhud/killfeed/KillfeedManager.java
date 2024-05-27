package dev.kuylar.bwplayerhud.killfeed;

import java.util.ArrayList;

public class KillfeedManager {
	private static ArrayList<KillfeedEntry> entries = new ArrayList<>();

	public static void addEntry(KillfeedEntry entry) {
		entries.add(entry);
	}

	public static KillfeedEntry[] getRenderableEntries() {
		for (int i = entries.size() - 1; i >= 0; i--) {
			KillfeedEntry entry = entries.get(i);
			if (entry.timestamp < System.currentTimeMillis() - 5000) {
				entries.remove(i);
			}
		}
		return entries.toArray(new KillfeedEntry[0]);
	}
}
