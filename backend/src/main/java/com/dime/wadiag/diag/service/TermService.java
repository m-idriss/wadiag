package com.dime.wadiag.diag.service;

import java.io.IOException;
import java.util.List;

import com.dime.wadiag.diag.model.Term;

public interface TermService {
    Term save(String word) throws IOException;

    List<Term> findAll();

    Term findByWord(String word);

    int deleteByWord(String word);

}
