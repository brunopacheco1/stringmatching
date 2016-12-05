package ru.fuzzysearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

	public static String[] loadDictionary(File file) throws IOException {
		List<String> lines = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(java.text.Normalizer.normalize(line, java.text.Normalizer.Form.NFD).toUpperCase().replaceAll("[^\\p{ASCII}]", "").replaceAll("[^A-Za-z0-9]", ""));
		}
		reader.close();
		return lines.toArray(new String[lines.size()]);
	}
}
