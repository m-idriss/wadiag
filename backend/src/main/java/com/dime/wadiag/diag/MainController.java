package com.dime.wadiag.diag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController("wadiag.mainControler")
public class MainController {

  @Operation(summary = "Welcome")
  @GetMapping("/")
  public String index() {
    return "You're lost !";
  }

  @Operation(summary = "Welcome")
    @GetMapping("/{name}")
  public String lost(@PathVariable("name") String name) {
    return "You're lost ! " + name;
  }
}