package com.payday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.payday.repository.UserRepository;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
// curl -i  -v localhost:8001/api/v1/lottery/numbers -u jack:secret_123
@SpringBootApplication
public class PaydayAuthenticationApplication implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(PaydayAuthenticationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.findAll().forEach(System.out::println);
	}

}
