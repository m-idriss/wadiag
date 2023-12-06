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
  public ResponseEntity<Word> saveWord(@PathVariable(name = "word", required = true) String word)
      throws ResourceNotFoundException, IOException {
    Word existingWord = service.findByName(word);
    if (existingWord == null) {
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(word));
    }
    return ResponseEntity.ok(existingWord);
  }

  @Operation(summary = "DeleteWord")
  @DeleteMapping("/{word}")
  public ResponseEntity<String> deleteByName(@PathVariable String word) {
    try {
      int deletedCount = service.deleteByName(word);
      if (deletedCount == 0) {
        return ResponseEntity.status(204).build();
      }
      return ResponseEntity.ok(deletedCount + " word(s) deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error deleting word: " + e.getMessage());
    }
  }

}