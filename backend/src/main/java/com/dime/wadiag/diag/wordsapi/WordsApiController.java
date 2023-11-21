package com.dime.wadiag.diag.wordsapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/rest/synonyms")
public class WordsApiController {
        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private WordsApiServiceImpl service;

        @Operation(summary = "Get a synonyms for word")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found the synonyms", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = WordsApiResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Synonyms not found", content = @Content) })
        @GetMapping("/{word}")
        public ResponseEntity<WordsApiResponse> getSynonymsForWord(@PathVariable("word") String word)
                        throws ResourceNotFoundException, IOException {
                WordsApiResponse wordsApiResponse = service.getSynonymsForWord(word);
                WordsApiResponse dto = modelMapper.map(wordsApiResponse, WordsApiResponse.class);
                
                return ResponseEntity.ok(dto);
        }

}
