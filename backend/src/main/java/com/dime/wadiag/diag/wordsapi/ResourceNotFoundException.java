package com.dime.wadiag.diag.wordsapi;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID = -5434140839578099309L;
	private final String type;
    private final String word;

    public ResourceNotFoundException(String type, String word) {
        super(String.format("%s not found for : %s", type, word));
        this.type = type;
        this.word = word;
    }

}
