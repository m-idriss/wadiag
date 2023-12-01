package com.dime.wadiag.diag.service;

import java.io.IOException;
import java.util.List;

import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.wordsapi.ResourceNotFoundException;

public interface WordService {
    Word save(String word) throws ResourceNotFoundException, IOException;

    List<Word> findAll();

    Word findByName(String word);

}
