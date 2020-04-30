package com.practice.wordcounter.persistence;

import java.util.HashMap;
import java.util.Map;
/**
 * Class to implement WordPersistence interface to save the words
 * Implemented as Map
 */
public class WordPersistenceMapImpl implements WordPersistence {

	private Map<String, Integer> wordMap;
	
	public WordPersistenceMapImpl() {
		wordMap = new HashMap<>();
	}

	/**
	 * @param word    word to be saved 
	 */
	@Override
	public void saveWord(String word) {
		Integer counter = null;
		if((counter = wordMap.get(word))== null) {
			wordMap.put(word, 1);
		}
		else {
			wordMap.put(word, counter+1);
		}
	}

	/**
	 * @param word     word to check if present
	 * @return         count of how many times the word was added
	 */
	@Override
	public int getWordCount(String word) {
		Integer count = wordMap.get(word);
		return count == null? 0 : count;
	}

}
