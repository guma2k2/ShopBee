package com.web.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = "$2a$10$WMGH6FHXyJhOxHRcF8E91uvh6dimNRGEtPypFKPnN6hjnHbEdtrne";
		String encode = bCryptPasswordEncoder.encode(password);
		System.out.print(encode);
		
		boolean matches= bCryptPasswordEncoder.matches(password, encode);
		assertThat(matches).isTrue();
	}

}
