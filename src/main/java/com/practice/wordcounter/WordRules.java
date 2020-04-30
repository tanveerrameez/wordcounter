package com.practice.wordcounter;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * Class encapsulating the rules for the word
 * default implementation - only alphabetic characters allowed
 * and ignoreCase is false
 *
 */
public class WordRules {

	private boolean ignoreCase;

	public boolean isValidWord(String word) {
		return !StringUtils.isEmpty(word) 
				&& word.chars().allMatch(Character::isLetter);
	}
}
