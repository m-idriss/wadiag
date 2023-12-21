package com.dime.wadiag.diag.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.dime.wadiag.diag.model.Term;

public interface TermService {
    Optional<Term> create(String word) throws IOException;

    Optional<List<Term>> findAll();

    Optional<Term> findById(Long id);

    Optional<Term> findByWord(String word);

    Optional<Integer> deleteByWord(String word);

}
