package com.practice.wordcounter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.practice.wordcounter.persistence.WordPersistence;
import com.practice.wordcounter.persistence.WordPersistenceMapImpl;
/**
 * Test class to test the basic Map implementation of storing words
 *
 */
public class WordPersistenceMapImplTest {

	private WordPersistence wordPersistence;
	private String word1="TestWord";
	private String word1Duplicate="TestWord";
	private String word2="AnotherTestWord";
	
	@BeforeEach
	void setUp() throws Exception {
		wordPersistence = new WordPersistenceMapImpl();
	}

	@Test
	void saveWordTest() {
		wordPersistence.saveWord(word1);
		assertEquals(1,wordPersistence.getWordCount(word1));
	}
	
	@Test
	void saveMultipleWordTest() {
		wordPersistence.saveWord(word1);
		wordPersistence.saveWord(word2);
		wordPersistence.saveWord(word1Duplicate);
		
		assertEquals(2,wordPersistence.getWordCount(word1));
		assertEquals(2,wordPersistence.getWordCount(word1Duplicate));
		assertEquals(1,wordPersistence.getWordCount(word2));
	}
	
	@Test
	void noWordPresentTest() {
		assertEquals(0, wordPersistence.getWordCount(word1));
	}
	

}
