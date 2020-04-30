package com.practice.wordcounter;

import com.practice.wordcounter.exception.InvalidWordException;
import com.practice.wordcounter.persistence.WordPersistence;
import com.practice.wordcounter.persistence.WordPersistenceMapImpl;
import com.practice.wordcounter.thirdparty.translation.Translator;

/**
 * Class that implements WordCounter
 * 
 */
public class WordCounterImpl implements WordCounter {

	private WordPersistence wordStore;
	private Translator translator;
	private WordRules rules;
	private static final String invalidWordMessage = "Word %s is invalid";

	/**
	 * Default Constructor. The default implementation of the WordPersistence is
	 * WordPersistenceMapImpl.
	 */
	public WordCounterImpl() {
		this.wordStore = new WordPersistenceMapImpl();
		this.rules = new WordRules();
	}

	/**
	 * Constructor
	 * 
	 * @param translator The translator implementation
	 */
	public WordCounterImpl(Translator translator) {
		this();
		this.setTranslator(translator);
	}

	/**
	 * Adds a word. The word is validated and translated if required before being
	 * saved
	 * 
	 * @param word the word to be added
	 */
	@Override
	public void addWord(String word) {
		if (rules.isValidWord(word)) {
			if (translator != null) {
				word = translator.translate(word);
			}
			if (isIgnoreCase()) {
				word = word.toUpperCase();
			}
			wordStore.saveWord(word);
		} else
			throw new InvalidWordException(String.format(invalidWordMessage, word));
	}

	/**
	 * @param The word to be searched
	 * @return the number of times the word was added
	 */
	@Override
	public int wordCount(String word) {
		if (rules.isValidWord(word)) {
			if (translator != null) {
				word = translator.translate(word);
			}
			if (isIgnoreCase()) {
				word = word.toUpperCase();
			}
			return wordStore.getWordCount(word);
		} else
			throw new InvalidWordException(String.format(invalidWordMessage, word));
	}

	private boolean isIgnoreCase() {
		return rules.isIgnoreCase();
	}

	/**
	 * To set whether case should be ignore when comparing words. Default is false
	 * 
	 * @param ignoreCase boolean to indicate if case should be ignored
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		rules.setIgnoreCase(ignoreCase);
	}

	public Translator getTranslator() {
		return translator;
	}

	/**
	 * Set the Translator implementation
	 * 
	 * @param translator
	 */
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

}
