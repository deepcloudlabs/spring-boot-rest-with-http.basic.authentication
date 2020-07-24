package com.payday.provider;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.payday.repository.UserRepository;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class LdapAuthenticationProvider implements AuthenticationProvider {
	private final static int SIZE_SHA1_HASH = 20;

	private UserRepository userRepository;

	public LdapAuthenticationProvider(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var username = authentication.getPrincipal().toString();
		var password = authentication.getCredentials().toString();
		var user = userRepository.findByUsername(username);
		if (user.isEmpty())
			throw new AccessDeniedException("could not login");
		var originalEncodedPassword = new String(user.get().getPassword());
		var salt = getSalt(originalEncodedPassword);
		var encodedPassword = "";
		try {
			encodedPassword = getSshaDigestFor(password, salt);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if (!originalEncodedPassword.equalsIgnoreCase(encodedPassword)) {
			throw new AccessDeniedException("Wrong credentials");
		}
		var authority = new SimpleGrantedAuthority("USER");
		return new UsernamePasswordAuthenticationToken(username, password, List.of(authority));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	// The salt is the remaining part after the SHA1_hash
	private byte[] getSalt(String encodedPasswordWithSSHA) {
		byte[] data = Base64.getMimeDecoder().decode(encodedPasswordWithSSHA.substring(6));
		return Arrays.copyOfRange(data, SIZE_SHA1_HASH, data.length);
	}

	private static String byteArrayToHex(byte[] a) {
		var sb = new StringBuilder(a.length * 2);
		for (var b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	private String getSshaDigestFor(String password, byte[] salt) throws Exception {
		var crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(password.getBytes(Charset.forName("UTF-8")));
		crypt.update(salt);
		var hash = crypt.digest();

		var hashPlusSalt = new byte[hash.length + salt.length];
		System.arraycopy(hash, 0, hashPlusSalt, 0, hash.length);
		System.arraycopy(salt, 0, hashPlusSalt, hash.length, salt.length);

		return "{SSHA}" + Base64.getEncoder().encodeToString(hashPlusSalt);
	}
}
