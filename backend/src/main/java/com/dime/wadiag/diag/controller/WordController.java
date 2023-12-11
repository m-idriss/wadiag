package com.dime.wadiag.diag.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
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
  public ResponseEntity<Word> saveWord(@PathVariable(name = "word", required = true) String word)
      throws IOException {
    String name = word.toLowerCase();
    Word existingWord = service.findByName(name);
    if (existingWord == null) {
      Word entity = service.save(name);
      return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }
    return ResponseEntity.ok(existingWord);
  }

  @Operation(summary = "DeleteWord")
  @DeleteMapping("/{word}")
  public ResponseEntity<String> deleteByName(@PathVariable String word) {
    try {
      String name = word.toLowerCase();
      int deletedCount = service.deleteByName(name);
      if (deletedCount == 0) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.ok(deletedCount + (deletedCount > 1 ? " words" : " word") + " deleted successfully");
    } catch (Exception e) {
      log.warn("Exception occurred", e);
      return ResponseEntity.status(500).body("Error when deleting word");
    }
  }

}