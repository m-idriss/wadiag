package com.dime.wadiag.diag.service.impl;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.mapper.WordMapper;
import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.repository.WordRepository;
import com.dime.wadiag.diag.service.WordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository repository;
    private final WordsApiServiceImpl service;

    private final WordMapper wordMapper = new WordMapper(new ModelMapper());

    @Override
    public Word save(String name) throws IOException {
        WordDto wordDto = service.getSynonymsForWord(name);
        return repository.save(wordMapper.fromWordDto(wordDto));
    }

    @Override
    public List<Word> findAll() {
        return repository.findAll();
    }

    @Override
    public Word findByName(String name) {
        Word entity = repository.findByName(name);
        log.debug("Found word: {}", entity);
        return entity;
    }

    @Override
    public int deleteByName(String name) {
        int count = repository.deleteByName(name);
        log.debug("delete {} count(s) of word: {}", count, name);
        return count;
    }
}
