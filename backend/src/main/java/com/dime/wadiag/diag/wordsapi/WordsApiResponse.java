package com.dime.wadiag.diag.wordsapi;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WordsApiResponse {

    private String word;

    private List<String> synonyms;
}
