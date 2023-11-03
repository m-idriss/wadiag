package com.dime.wadiag.diag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController("wadiag.WordController")
@RequestMapping("/rest/words")
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
  public ResponseEntity<Word> save(@PathVariable("word") String word) {
    return ResponseEntity.ok(service.save(word));
  }

}