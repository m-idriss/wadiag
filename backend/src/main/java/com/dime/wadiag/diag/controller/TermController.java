package com.dime.wadiag.diag.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.diag.exception.WadiagError;
import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.TermService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rest/terms")
@Tag(name = "Terms", description = "Manage Terms")
public class TermController {

  @Autowired
  private TermService termService;

  @Operation(summary = "Find All Terms")
  @GetMapping
  public ResponseEntity<List<Term>> findAllTerms() {
    return ResponseEntity.ok(termService.findAll().orElse(Collections.emptyList()));
  }

  @Operation(summary = "Get Term by ID")
  @GetMapping("/{id}")
  public ResponseEntity<Term> getTermById(@PathVariable Long id) {
    return termService.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> WadiagError.TERM_NOT_FOUND.exWithArguments(Map.of("id", id)));
  }

  @Operation(summary = "Create Term")
  @PostMapping("/{word}")
  public ResponseEntity<Term> createTerm(@PathVariable String word) throws IOException {
    String wordLower = word.toLowerCase();
    Optional<Term> existingTerm = termService.findByWord(wordLower);
    Term term = null;
    if (existingTerm.isPresent()) {
      term = existingTerm.get();
    } else {
      term = termService.create(wordLower)
          .orElseThrow(() -> WadiagError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower)));
    }
    return ResponseEntity.created(URI.create("/rest/terms/" + term.getId())).body(term);
  }

  @Operation(summary = "Delete Term by Word")
  @DeleteMapping("/{word}")
  public ResponseEntity<Void> deleteTerm(@PathVariable String word) {
    String wordLower = word.toLowerCase();
    return termService.deleteByWord(wordLower)
        .map(count -> ResponseEntity.noContent().<Void>build())
        .orElseThrow(() -> WadiagError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower)));
  }
}
