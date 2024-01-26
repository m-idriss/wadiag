package com.dime.wadiag.diag.model;

import java.util.Date;

import org.springframework.hateoas.server.core.Relation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Relation(collectionRelation = "log")
public class LogData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String logUUID;
    private Date dateOfCreation = new Date();
    private String content;
    private String key;
    private int httpStatus;
    private String message;
}
