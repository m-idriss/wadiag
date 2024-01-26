package com.dime.wadiag.kafka;

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
public class LogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String topic;
    private Date dateOfCreation = new Date();
    private String content;
    private String detail;
    private int httpStatus;
    private String message;
}
