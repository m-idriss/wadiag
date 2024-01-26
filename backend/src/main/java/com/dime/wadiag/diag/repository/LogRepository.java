package com.dime.wadiag.diag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dime.wadiag.diag.model.LogData;

@Repository
public interface LogRepository extends JpaRepository<LogData, Long> {

}