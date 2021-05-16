package ganymedes01.etfuturum.client.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
	 * Creates weighted RNG to simulate the weights on Nether ambience sounds in 1.16
	 * Author Philipp from Stack Overflow
	 */
public class WeightedSoundPool {
	
	private class Entry {
		Entry() {}
		
		double weight;
		String object;
	}

	private List<Entry> entries = new ArrayList<Entry>();
	private double accumulatedWeight;
	private Random rand = new Random();

	public void addEntry(String object, double weight) {
		accumulatedWeight += weight;
		Entry e = new Entry();
		e.object = object;
		e.weight = accumulatedWeight;
		entries.add(e);
	}

	public String getRandom() {
		double r = rand.nextDouble() * accumulatedWeight;

		for (Entry entry: entries) {
			if (entry.weight >= r) {
				return entry.object;
			}
		}
		return null;
	}
}