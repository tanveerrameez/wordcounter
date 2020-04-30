package com.practice.wordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.practice.wordcounter.exception.InvalidWordException;
import com.practice.wordcounter.thirdparty.translation.Translator;

public class WordCounterTest {

	private WordCounter wordCounter;
	private Translator translator ;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		translator = mock(Translator.class);
//		WordRules rules= new WordRules();
		wordCounter=new WordCounterImpl(translator);
		when(translator.translate("flor")).thenReturn("flower");
		when(translator.translate("blume")).thenReturn("flower");
	}

	@Test
	void singleWordTest() {
		String word="test";
		wordCounter.addWord(word);
		int counter = wordCounter.wordCount(word);
		assertEquals(1, counter);
	}
	
	
	@Test
	void multipleWordTest() {
		String word="test";
		wordCounter.addWord(word);
		wordCounter.addWord(word);
		int counter = wordCounter.wordCount(word);
		assertEquals(2, counter);
	}
	
	@Test
	void differentCaseWordTest() {
		String word1="test";
		String word2="Test";
		
		wordCounter.addWord(word1);
		wordCounter.addWord(word2);
		int counter = wordCounter.wordCount(word1);
		counter = wordCounter.wordCount(word2);
		assertEquals(1, counter);
		assertEquals(1, counter);
	}
	
	@Test
	void sameWordInDifferentLanguageTest() {
		String wordInEnglish="flower";
		wordCounter.addWord(wordInEnglish);
		String wordInGerman="flor";
		wordCounter.addWord(wordInEnglish);
		int counter = wordCounter.wordCount(wordInGerman);
		assertEquals(2, counter);
	}

	
	@Test
	void nonAlphabeticWordTest() {
		String word="12345A";
		assertThrows(InvalidWordException.class, ()-> wordCounter.addWord(word));
	}
	
	@Test
	void nullWordTest() {
		String word=null;
		assertThrows(InvalidWordException.class, ()-> wordCounter.addWord(word));
	}
	
	@Test
	void nullWordCountTest() {
		String word=null;
		assertThrows(InvalidWordException.class, ()-> wordCounter.wordCount(word));
	}
	
	@Test
	void noWordCountTest() {
		String word = "test";
		assertEquals(0,wordCounter.wordCount(word));
	}
	

}
