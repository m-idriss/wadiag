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

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.TermService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rest/terms")
@Tag(name = "Terms", description = "Manage Terms")
public class TermController {

  @Autowired
  private TermService service;

  @Operation(summary = "FindAllTerms")
  @GetMapping()
  public ResponseEntity<List<Term>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "SaveTerm")
  @PostMapping("/{word}")
  public ResponseEntity<Term> saveWord(@PathVariable(name = "word", required = true) String word)
      throws IOException {
    String wordLower = word.toLowerCase();
    Term existingTerm = service.findByWord(wordLower);
    if (existingTerm == null) {
      Term entity = service.save(wordLower);
      return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }
    return ResponseEntity.ok(existingTerm);
  }

  @Operation(summary = "DeleteTerm")
  @DeleteMapping("/{word}")
  public ResponseEntity<String> deleteByWord(@PathVariable String word) {
    try {
      String wordLower = word.toLowerCase();
      int deletedCount = service.deleteByWord(wordLower);
      if (deletedCount == 0) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.ok(deletedCount + (deletedCount > 1 ? " terms" : " term") + " deleted successfully");
    } catch (Exception e) {
      log.warn("Exception occurred", e);
      return ResponseEntity.status(500).body("Error when deleting term");
    }
  }

}