package com.example.rest.webServices.restfulwebServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// This is the controller
// This would be handling all http requests
@RestController
public class HelloWorldController {

	@GetMapping(path = "/helloDuniya")
	public String HelloDuniya() {
		return "Hello Duniya !!! Mai aa raha hoon!!";
	}

	@GetMapping(path = "/helloDuniyaBean/{name}")
	public HelloWorldBean HelloDuniyaBean(@PathVariable String name) {
		return new HelloWorldBean("You are doing well " + name);
	}
}
