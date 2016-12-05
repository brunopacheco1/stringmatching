package ru.fuzzysearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FuzzySearch {

	public static Object loadObject(String filename) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(filename);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		Object object = objectInputStream.readObject();

		objectInputStream.close();
		fileInputStream.close();
		return object;
	}

	public static void saveObject(Object object, String filename) throws Exception {
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(object);

		objectOutputStream.close();
		fileOutputStream.close();
	}

	public static interface SearchFactory {

		public String getName();

		public String getFilename();

		public Index newIndex(String[] dictionary);

		public Searcher newSearcher(Index index);
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("file.encoding", "UTF-8");
		
		NumberFormat format = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		
		SearchFactory signHashFactory = new SearchFactory() {

			public String getFilename() {
				return "signhash_index.txt";
			}

			public String getName() {
				return "SignHash Method";
			}

			public Index newIndex(String[] dictionary) {
				return new SignHashIndexer(new Alphabet()).createIndex(dictionary);
			}

			public Searcher newSearcher(Index index) {
				return new SignHashSearcher((SignHashIndex) index, new DamerauLevensteinMetric(), K);
			}
		};
		
		SearchFactory[] factories = new SearchFactory[] {signHashFactory};
		
		Map<Integer, File> files = new HashMap<>();
		
		for(File dictFile : new File(FuzzySearch.class.getResource("/dicts").getFile()).listFiles()) {
			files.put(Integer.parseInt(dictFile.getName().replaceAll("\\D", "")), dictFile);
		}
		
		List<Integer> chaves = new ArrayList<>(files.keySet());
		
		Collections.sort(chaves);
		Collections.reverse(chaves);
		
		for(Integer chave : chaves) {
			File dictFile = files.get(chave);
			
			String dictFileStr = dictFile.getAbsolutePath();
			
			String[] dictionary = Dictionary.loadDictionary(dictFile);
			
			for (SearchFactory factory : factories) {
				Index index = factory.newIndex(dictionary);
	
				String indexFile = PATH + factory.getFilename();
				saveObject(index, indexFile);
			}
	
			for (SearchFactory factory : factories) {
				String indexFile = PATH + factory.getFilename();
				Index index = (Index) loadObject(indexFile);
	
				Searcher searcher = factory.newSearcher(index);
	
				int step = dictionary.length / TEST_COUNT;
				long startTime = System.currentTimeMillis();
	
				int count = 0;
	
				for (int i = 0; i < dictionary.length; i += step) {
					String word = dictionary[i];
					if (word.length() >= MIN_LENGTH) {
						searcher.search(word);
//						searcher.getIndex().getDictionary()[300028]
						++count;
					}
				}
	
				long endTime = System.currentTimeMillis();
	
				System.out.println(factory.getName() + "\t" + dictionary.length + "\t" + format.format((double) (endTime - startTime) / count));
			}
	
			OnlineSearcher[] onlineSearchers = new OnlineSearcher[] { new BitapOnlineSearcher(new Alphabet()) };
	
			for (OnlineSearcher onlineSearcher : onlineSearchers) {
				int step = dictionary.length / TEST_COUNT;
				long startTime = System.currentTimeMillis();
	
				int count = 0;
				for (int i = 0; i < dictionary.length; i += step) {
					String word = dictionary[i];
					if (word.length() >= MIN_LENGTH) {
						Reader reader = new BufferedReader(new FileReader(dictFileStr));
						onlineSearcher.search(reader, word, K);
						++count;
					}
				}
				long endTime = System.currentTimeMillis();
	
				System.out.println(onlineSearcher.getName() + "\t" + dictionary.length + "\t" + format.format((double) (endTime - startTime) / count));
			}
		}
	}

	private static final String PATH = System.getProperty("user.home") + "/";
	private static final int K = 2;
	private static final int TEST_COUNT = 10;
	private static final int MIN_LENGTH = 3;
}
