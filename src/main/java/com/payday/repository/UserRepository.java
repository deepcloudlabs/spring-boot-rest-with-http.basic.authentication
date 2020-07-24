package com.payday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.ldap.repository.LdapRepository;

import com.payday.domain.User;

public interface UserRepository extends LdapRepository<User> {
	Optional<User> findByUsername(String username);
    List<User> findByUsernameLikeIgnoreCase(String username);
}
