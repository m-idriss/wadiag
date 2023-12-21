package com.dime.wadiag.diag.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.repository.TermRepository;
import com.dime.wadiag.diag.service.TermService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository repository;
    private final WordsApiServiceImpl service;

    @Override
    public Optional<Term> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<List<Term>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }

    @Override
    public Optional<Term> findByWord(String word) {
        return Optional.ofNullable(repository.findByWord(word));
    }

    @Override
    public Optional<Term> save(String word) throws IOException {
        return Optional.of(repository.save(service.getSynonymsForWord(word)));
    }

    @Override
    public Optional<Integer> deleteByWord(String word) {
        return Optional.of(repository.deleteByWord(word));
    }
}
