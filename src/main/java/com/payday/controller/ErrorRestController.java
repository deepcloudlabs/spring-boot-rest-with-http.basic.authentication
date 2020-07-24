package com.payday.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@RestController
@RequestMapping("error")
@RequestScope
@CrossOrigin
public class ErrorRestController {

	@GetMapping()
	public ResponseEntity<String> error() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
	}
}
