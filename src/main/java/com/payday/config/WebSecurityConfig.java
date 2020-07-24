package com.payday.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.payday.provider.LdapAuthenticationProvider;
import com.payday.repository.UserRepository;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserRepository userRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.GET, "/lottery/error/**").permitAll()
				.antMatchers(HttpMethod.GET, "/lottery/error/**").anonymous().antMatchers(HttpMethod.GET, "/error/**")
				.anonymous().antMatchers(HttpMethod.GET, "/lottery/numbers/**").authenticated();
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ldapAuthenticationProvider());
	}

	@Bean
	LdapAuthenticationProvider ldapAuthenticationProvider() {
		return new LdapAuthenticationProvider(userRepository);
	}
}