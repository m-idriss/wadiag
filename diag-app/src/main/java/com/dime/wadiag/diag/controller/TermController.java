package com.dime.wadiag.diag.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.diag.exception.GenericError;
import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.model.TermModelAssembler;
import com.dime.wadiag.diag.service.TermService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/*
 * https://www.codejava.net/frameworks/spring-boot/rest-api-crud-with-hateoas-tutorial
 */
@RestController
@RequestMapping("api/v1/terms")
@AllArgsConstructor
@Tag(name = "Terms", description = "Manage Terms")
public class TermController {

  private final TermService termService;
  private final TermModelAssembler modelAssembler;

  @Operation(summary = "Find All Terms")
  @GetMapping
  public CollectionModel<EntityModel<Term>> listAll() {
    return termService.findAll()
        .map(terms -> terms.stream().map(modelAssembler::toModel).collect(Collectors.toList()))
        .map(termModels -> CollectionModel.of(termModels)
            .add(linkTo(methodOn(TermController.class).listAll()).withSelfRel()))
        .orElse(CollectionModel.of(Collections.emptyList()));
  }

  @Operation(summary = "Get Term by ID")
  @GetMapping("/{id:\\d+}")
  public ResponseEntity<EntityModel<Term>> getOne(@PathVariable Long id) {
    return termService.findById(id)
        .map(term -> ResponseEntity.ok(modelAssembler.toModel(term)))
        .orElseThrow(() -> GenericError.TERM_NOT_FOUND.exWithArguments(Map.of("id", id)));
  }

  @Operation(summary = "Get Term by Word")
  @GetMapping("/{word:\\D+}")
  public ResponseEntity<EntityModel<Term>> createTerm(@PathVariable String word) throws IOException {
    String wordLower = word.toLowerCase();

    Optional<Term> existingTerm = termService.findByWord(wordLower);
    if (existingTerm.isPresent()) {
      return ResponseEntity.ok(modelAssembler.toModel(existingTerm.get()));
    }

    return termService.create(wordLower)
        .map(term -> ResponseEntity.created(linkTo(methodOn(TermController.class)
            .getOne(term.getId())).toUri()).body(modelAssembler.toModel(term)))
        .orElseThrow(() -> GenericError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower)));
  }

  @Operation(summary = "Delete Term by Word")
  @DeleteMapping("/{word}")
  public ResponseEntity<Void> deleteTerm(@PathVariable String word) {
    String wordLower = word.toLowerCase();

    return termService.deleteByWord(wordLower)
        .filter(deleteCount -> deleteCount > 0)
        .map(d -> ResponseEntity.noContent().<Void>build())
        .orElseThrow(() -> GenericError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower)));
  }
}