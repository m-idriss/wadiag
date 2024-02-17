package com.dime.wadiag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dime.wadiag.model.LogModel;

public interface LogRepository extends JpaRepository<LogModel, Long> {

}