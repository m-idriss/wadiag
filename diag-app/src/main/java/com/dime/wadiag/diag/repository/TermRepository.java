package com.dime.wadiag.diag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dime.wadiag.diag.model.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    Term findByWord(String word);

    @Transactional
    int deleteByWord(String name);

}