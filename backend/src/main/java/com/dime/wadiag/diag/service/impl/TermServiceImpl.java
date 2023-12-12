package com.dime.wadiag.diag.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.repository.TermRepository;
import com.dime.wadiag.diag.service.TermService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository repository;
    private final WordsApiServiceImpl service;

    @Override
    public Term save(String word) throws IOException {
        Term term = service.getSynonymsForWord(word);
        return repository.save(term);
    }

    @Override
    public List<Term> findAll() {
        return repository.findAll();
    }

    @Override
    public Term findByWord(String word) {
        Term entity = repository.findByWord(word);
        log.debug("Found word: {}", entity);
        return entity;
    }

    @Override
    public int deleteByWord(String word) {
        int count = repository.deleteByWord(word);
        log.debug("delete {} count(s) of word: {}", count, word);
        return count;
    }
}
