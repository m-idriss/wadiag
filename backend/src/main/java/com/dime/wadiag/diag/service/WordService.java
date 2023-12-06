package com.dime.wadiag.diag.service;

import java.io.IOException;
import java.util.List;

import com.dime.wadiag.diag.model.Word;

public interface WordService {
    Word save(String name) throws IOException;

    List<Word> findAll();

    Word findByName(String name);

    int deleteByName(String name);

}
