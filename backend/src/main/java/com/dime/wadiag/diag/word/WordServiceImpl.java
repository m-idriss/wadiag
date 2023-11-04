package com.dime.wadiag.diag.word;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository dao;

    @Override
    public Word save(String word) {
        Word entity = Word.builder()
                .name(word)
                .build();
        entity = dao.save(entity);
        log.debug(entity.toString());
        return entity;
    }

    @Override
    public List<Word> findAll() {
        return dao.findAll();
    }

    @Override
    public Word findByName(String word) {
        Word entity = dao.findByName(word);
        log.debug(entity.toString());
        return entity;
    }
}
