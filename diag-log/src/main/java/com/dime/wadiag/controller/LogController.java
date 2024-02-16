package com.dime.wadiag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.service.LogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/logs")
@AllArgsConstructor
@Tag(name = "Logs", description = "Manage Logs")
public class LogController {

    private final LogService service;

    @Operation(summary = "Create Log by")
    @PostMapping
    public ResponseEntity<LogModel> save(@RequestBody LogModel logModel) {
        return ResponseEntity.ok(service.save(logModel));
    }

    @Operation(summary = "Get Log by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LogModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Find All Logs")
    @GetMapping
    public ResponseEntity<List<LogModel>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Delete Log by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().<Void>build();
    }

}