package com.dime.wadiag.diag.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.service.WordService;
import com.dime.wadiag.diag.wordsapi.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rest/words")
@Tag(name = "Words", description = "Manage Words")
public class WordController {

  @Autowired
  private WordService service;

  @Operation(summary = "FindAllWord")
  @GetMapping()
  public ResponseEntity<List<Word>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "SaveWord")
  @PostMapping("/{word}")
  public ResponseEntity<Word> save(@PathVariable("word") String word) throws ResourceNotFoundException, IOException {
    return ResponseEntity.ok(service.save(word));
  }

}