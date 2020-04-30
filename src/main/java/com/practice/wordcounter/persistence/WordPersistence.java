package com.practice.wordcounter.persistence;

public interface WordPersistence {

	void saveWord(String word);
	int getWordCount(String word);

}
