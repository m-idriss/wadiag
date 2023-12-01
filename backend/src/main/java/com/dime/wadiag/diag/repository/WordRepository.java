package com.dime.wadiag.diag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dime.wadiag.diag.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Word findByName(String string);

}
