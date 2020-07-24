package com.payday.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
@RequestMapping("numbers")
@RequestScope
@CrossOrigin
public class LotteryRestController {

	// http://localhost:8001/api/v1/lottery/numbers
	@GetMapping
	public List<Integer> getNumbers() {
		return ThreadLocalRandom.current().ints(1, 50).distinct().limit(6).sorted().boxed()
				.collect(Collectors.toList());
	}
}
