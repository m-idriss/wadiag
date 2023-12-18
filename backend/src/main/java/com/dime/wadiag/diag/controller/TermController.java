package com.dime.wadiag.diag.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.TermService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest/terms")
@Tag(name = "Terms", description = "Manage Terms")
@Slf4j
public class TermController {

  @Autowired
  private TermService termService;

  @Operation(summary = "Find All Terms")
  @GetMapping
  public ResponseEntity<List<Term>> findAllTerms() {
    List<Term> allTerms = termService.findAll();
    if (allTerms.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allTerms, HttpStatus.OK);
  }

  @Operation(summary = "Get Term by ID")
  @GetMapping("/{id}")
  public ResponseEntity<Term> getTermById(@PathVariable Long id) {
    return termService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Save Term")
  @PostMapping("/{word}")
  public ResponseEntity<Term> saveTerm(@PathVariable(name = "word") String word) throws IOException {
    String wordLower = word.toLowerCase();
    Term existingTerm = termService.findByWord(wordLower);
    if (existingTerm == null) {
      Term savedTerm = termService.save(wordLower);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedTerm);
    }
    return ResponseEntity.ok(existingTerm);
  }

  @Operation(summary = "Delete Term by Word")
  @DeleteMapping("/{word}")
  public ResponseEntity<String> deleteByWord(@PathVariable String word) {
    try {
      String wordLower = word.toLowerCase();
      int deletedCount = termService.deleteByWord(wordLower);
      if (deletedCount == 0) {
        return ResponseEntity.noContent().build();
      }
      String responseMessage = String.format("%d term%s deleted successfully", deletedCount,
          (deletedCount > 1 ? "s" : ""));
      return ResponseEntity.ok(responseMessage);
    } catch (Exception e) {
      log.warn("Exception occurred", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when deleting term");
    }
  }
}
