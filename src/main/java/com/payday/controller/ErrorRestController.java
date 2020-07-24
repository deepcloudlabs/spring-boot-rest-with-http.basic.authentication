package com.payday.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
public class ErrorRestController {

	@GetMapping()
	public ResponseEntity<String> error() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
	}
}
