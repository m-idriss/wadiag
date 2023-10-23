package com.dime.wadiag.diag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("wadiag.mainControler")
public class MainController {

  @GetMapping("/")
  public String index() {
    return "You're lost !";
  }
}

