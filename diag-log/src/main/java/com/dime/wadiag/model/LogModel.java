package com.dime.wadiag.model;

import java.time.Instant;

import org.springframework.hateoas.server.core.Relation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "log")
public class LogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String topic;
    @Builder.Default
    private Instant dateOfCreation = Instant.now();
    private String content;
    private String detail;
    private int httpStatus;
    private String message;
}
