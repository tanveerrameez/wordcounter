package com.practice.wordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.practice.wordcounter.exception.InvalidWordException;
import com.practice.wordcounter.thirdparty.translation.Translator;

public class WordCounterTest {

	private WordCounter wordCounter;
	
	private Translator translator;
	
	private static final String word1 = "WordA";
	private static final String word2 = "WordB";
	private static final String word3 = "WordC";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		/*
		 * Mock the Translator since in live scenario a third party implementation will be used,
		 * which needs to be mocked
		 */
		translator = mock(Translator.class);
		//instantiate implementation of WordCounter which accepts a Translator implementation
		wordCounter = new WordCounterImpl(translator);
		//The generic mock implementation of Translator.translate 
		when(translator.translate(any(String.class))).then(AdditionalAnswers.returnsFirstArg());
	}

	@DisplayName("Single word Test")
	@Test
	void singleWordTest() {
		wordCounter.addWord(word1);
		assertEquals(1, wordCounter.wordCount(word1));
	}

	@DisplayName("Add word multiple times Test")
	@Test
	void multipleWordTest() {
		int n = 7;
		for (int i = 0; i < n; i++) {
			wordCounter.addWord(new String(word1));
		}
		assertEquals(n, wordCounter.wordCount(word1));
	}

	@DisplayName("Add Multiple words test")
	@ParameterizedTest
	@MethodSource("getWords")
	void mixedMultipleWordTest(String[] words, String wordToBeCounted, int expectedCount) {
		for (String word : words) {
			wordCounter.addWord(word);
		}
		assertEquals(expectedCount, wordCounter.wordCount(wordToBeCounted));
	}

	static Stream<Arguments> getWords() {
		return Stream.of(arguments(new String[] { word1 }, word1, 1), 
				arguments(new String[] { word2 }, word2, 1),
				arguments(new String[] { word1, word2 }, word1, 1),
				arguments(new String[] { word1, word2, word1 }, word1, 2),
				arguments(new String[] { word1, word2, word1, word2, word1 }, word1, 3),
				arguments(new String[] { word1, word2, word3, word2, word1 }, word2, 2));
	}

	@DisplayName("Case sensitive word Test")
	@Test
	void differentCaseWordTest() {
		wordCounter.addWord(word1);
		wordCounter.addWord(word1.toUpperCase());
		assertEquals(1, wordCounter.wordCount(word1));
		assertEquals(1, wordCounter.wordCount(word1.toUpperCase()));
	}

	@DisplayName("Same word in different language Test")
	@Test
	void sameWordInDifferentLanguageTest() {
		String wordInEnglish = "flower";
		String wordInSpanish = "flor";
		String wordInGerman = "blume";
		//the mock implementation of Translator.translate for specific words
		when(translator.translate(wordInSpanish)).thenReturn(wordInEnglish);
		when(translator.translate(wordInGerman)).thenReturn(wordInEnglish);

		wordCounter.addWord(wordInEnglish);
		//verify if the method is invoked 
		verify(translator, times(1)).translate(Mockito.any(String.class));
		wordCounter.addWord(wordInSpanish);
		verify(translator, times(2)).translate(Mockito.any(String.class));
		wordCounter.addWord(wordInGerman);
		verify(translator, times(3)).translate(Mockito.any(String.class));
		assertEquals(3, wordCounter.wordCount(wordInEnglish));
		assertEquals(3, wordCounter.wordCount(wordInSpanish));
		assertEquals(3, wordCounter.wordCount(wordInGerman));
	}

	@DisplayName("Non-alphabetic word Test")
	@ParameterizedTest
	@MethodSource("getNonAlphabeticWords")
	void nonAlphabeticWordTest(String description, String invalidWord) {
		assertThrows(InvalidWordException.class, () -> wordCounter.addWord(invalidWord));
	}

	static Stream<Arguments> getNonAlphabeticWords() {
		return Stream.of(arguments("All digits", "12345"), 
				arguments("All special characters","!£$%^&*"),
				arguments("Alphabets and digits", "abc123"),
				arguments("Alphabets and special characters", "!£$%^&*ABC"),
				arguments("1 digits and rest alphabets","abcd1"),arguments("space in word","abcd efg"),
				arguments("Blank word",""), arguments("Whitespace"," "));
	}
	@DisplayName("Null word Test")
	@Test
	void nullWordTest() {
		assertThrows(InvalidWordException.class, () -> wordCounter.addWord(null));
		verify(translator, times(0)).translate(Mockito.any(String.class));
	}

	@DisplayName("Null word get count Test")
	@Test
	void nullWordCountTest() {
		assertThrows(InvalidWordException.class, () -> wordCounter.wordCount(null));
		verify(translator, times(0)).translate(Mockito.any(String.class));
	}

	@DisplayName("Count for word not added Test")
	@Test
	void countforWordNotAddedTest() {
		assertEquals(0, wordCounter.wordCount(word1));
	}

}
