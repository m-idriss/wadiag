package com.dime.wadiag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "Main", description = "Get ready")
public class MainController {

  @Operation(summary = "Welcome")
  @GetMapping()
  public String index() {
    return "You're lost !";
  }

}