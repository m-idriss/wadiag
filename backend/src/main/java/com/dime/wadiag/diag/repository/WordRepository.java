package com.dime.wadiag.diag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dime.wadiag.diag.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Word findByName(String name);

    @Transactional
    int deleteByName(String name);

}