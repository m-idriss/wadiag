package com.dime.wadiag.diag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {

    Word findByName(String string);
}
