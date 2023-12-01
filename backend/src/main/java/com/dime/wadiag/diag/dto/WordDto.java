package com.dime.wadiag.diag.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WordDto {

    private String word;

    private Set<String> synonyms;
}
