package com.dime.wadiag.diag.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.dime.wadiag.diag.exception.GenericError;
import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.model.TermModelAssembler;
import com.dime.wadiag.diag.service.TermService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import java.util.Collections;

/*
 * https://www.codejava.net/frameworks/spring-boot/rest-api-crud-with-hateoas-tutorial
 */
@RestController
@RequestMapping("/rest/terms")
@AllArgsConstructor
@Tag(name = "Terms", description = "Manage Terms")
public class TermController {

  @Autowired
  private TermService termService;

  private TermModelAssembler modelAssembler;

  @Operation(summary = "Find All Terms")
  @GetMapping
  public CollectionModel<EntityModel<Term>> listAll() {
    List<Term> terms = termService.findAll().orElse(Collections.emptyList());

    List<EntityModel<Term>> entityModels = terms.stream()
        .map(modelAssembler::toModel)
        .collect(Collectors.toList());

    CollectionModel<EntityModel<Term>> collectionModel = CollectionModel.of(entityModels);
    collectionModel.add(linkTo(methodOn(TermController.class).listAll()).withSelfRel());

    return collectionModel;
  }

  @Operation(summary = "Get Term by ID")
  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<Term>> getOne(@PathVariable("id") Long id) {
    Optional<Term> term = termService.findById(id);
    if (term.isPresent()) {
      EntityModel<Term> model = modelAssembler.toModel(term.get());
      return new ResponseEntity<>(model, HttpStatus.OK);
    }
    throw GenericError.TERM_NOT_FOUND.exWithArguments(Map.of("id", id));
  }

  @Operation(summary = "Create Term")
  @PostMapping("/{word}")
  public ResponseEntity<EntityModel<Term>> createTerm(@PathVariable String word) throws IOException {
    String wordLower = word.toLowerCase();
    Optional<Term> existingTerm = termService.findByWord(wordLower);
    if (existingTerm.isPresent()) {
      EntityModel<Term> model = modelAssembler.toModel(existingTerm.get());
      return new ResponseEntity<>(model, HttpStatus.OK);
    }
    Term term = termService.create(wordLower)
        .orElseThrow(() -> GenericError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower)));
    EntityModel<Term> model = modelAssembler.toModel(term);
    return ResponseEntity.created(linkTo(methodOn(TermController.class)
        .getOne(term.getId())).toUri()).body(model);
  }

  @Operation(summary = "Delete Term by Word")
  @DeleteMapping("/{word}")
  public ResponseEntity<Void> deleteTerm(@PathVariable String word) {
    String wordLower = word.toLowerCase();
    Optional<Integer> count = termService.deleteByWord(wordLower);
    if (count.isPresent() && count.get() > 0) {
      return ResponseEntity.noContent().<Void>build();
    }
    throw GenericError.WORD_NOT_FOUND.exWithArguments(Map.of("word", wordLower));
  }
}
