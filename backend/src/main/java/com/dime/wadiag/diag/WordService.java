package com.dime.wadiag.diag;

import java.util.List;

public interface WordService {
    Word save(String word);

    List<Word> findAll();

    Word findByName(String word);

}
