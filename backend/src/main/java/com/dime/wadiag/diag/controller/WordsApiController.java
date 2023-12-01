package com.dime.wadiag.diag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.service.impl.WordsApiServiceImpl;
import com.dime.wadiag.diag.wordsapi.ResourceNotFoundException;

import java.io.IOException;

@RestController
@RequestMapping("/rest/synonyms")
@Tag(name = "Synonyms", description = "Manage Synonyms")
public class WordsApiController {
        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private WordsApiServiceImpl service;

        @Operation(summary = "Get a synonyms for word")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found the synonyms", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = WordDto.class)) }),
                        @ApiResponse(responseCode = "404", description = "Synonyms not found", content = @Content) })
        @GetMapping("/{word}")
        public ResponseEntity<WordDto> getSynonymsForWord(@PathVariable("word") String word)
                        throws ResourceNotFoundException, IOException {
                WordDto wordsApiResponse = service.getSynonymsForWord(word);
                WordDto dto = modelMapper.map(wordsApiResponse, WordDto.class);

                return ResponseEntity.ok(dto);
        }

}
