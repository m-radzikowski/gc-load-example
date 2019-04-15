package com.gwitczak.liczSlowa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
	private static List<Pair> m = new CopyOnWriteArrayList<>();
	private static List<Pair> n = Collections.synchronizedList(new ArrayList<>());

	public static void main(String[] args) throws IOException, InterruptedException {

		while (true) {
			Map<String, Long> map = countWords();
//            for (Map.Entry<String, Long> entry : map.entrySet()) {
//                if (Math.random() > 0.7) {
//                    System.out.println(entry.getKey());
//                    n.add(new Pair(entry.getKey(), String.valueOf(entry.getValue()), entry.getValue()));
//                }
//            }

			System.out.println(n.get((int) (Math.random() * n.size())));
			System.out.println(m.get((int) (Math.random() * m.size())));

			m = new CopyOnWriteArrayList<>();
			n = new ArrayList<>();

//            for (int i = 0; i < 0.25*n.size(); i++) {
//                n.remove(Math.random()*n.size());
//            }
//            for (int i = 0; i < 0.25*m.size(); i++) {
//                m.remove(Math.random()*m.size());
//            }
		}
	}

	private static Map<String, Long> countWords() throws FileNotFoundException, InterruptedException {
		final Map<String, Long> map = new TreeMap<>();
		try (Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("pantadeusz.txt"))) {
			while (scanner.hasNextLine()) {
				String[] words = scanner.nextLine().trim().toLowerCase().replaceAll("^[a-z ]", "").replaceAll(",-.;:", "").replaceAll("\\*!@#\\$%&*\\(\\)", "").split(" ");
				for (String word : words) {
					if (Math.random() > 0.99)
						map.compute(word, (s, aLong) -> aLong == null ? 0 : aLong + 1);
				}
				for (Map.Entry<String, Long> entry : map.entrySet()) {
					if (Math.random() > 0.10) {
						n.add(new Pair(entry.getKey(), String.valueOf(entry.getValue()), entry.getValue()));
						n.add(new Pair(entry.getKey() + "x", String.valueOf(entry.getValue()) + "dd", entry.getValue()));
					} else {
						m.add(new Pair(entry.getKey(), String.valueOf(entry.getValue()), entry.getValue()));
						if (Math.random() > 0.6) {
							m.add(new Pair(entry.getKey() + "x", String.valueOf(entry.getValue()) + "dd", entry.getValue()));
						}
					}
					if (Math.random() > 0.70 && n.size() > 10) {
						n.remove(Math.random() * n.size());
					} else if (m.size() > 10) {
						m.remove(Math.random() * m.size());
					}
				}
			}
		}
		return map;
	}
}
