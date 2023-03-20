package com.web.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	
	@Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new MyShopUserDetailService();
	}
	
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider() ;
		authenticationProvider.setUserDetailsService(new MyShopUserDetailService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	
	protected void configure(AuthenticationProvider auth){
		auth.authenticate((Authentication) daoAuthenticationProvider());
	}
	


	@Bean 
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	   return http
			   .authorizeHttpRequests()
			    .requestMatchers("/**").hasAuthority("Admin")
			    .requestMatchers("/client/**" , "/").hasAuthority("Customer")
			    .anyRequest().authenticated().and()
				.formLogin().loginPage("/login").usernameParameter("email").permitAll().and()
				.logout().permitAll().and()
				.rememberMe().key("AbcDefgHijKlmnOpqrs_1234567890")
				.tokenValiditySeconds(7 * 24 * 60 * 60).and()
			   .build();
	}
	@Bean
	 WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**" , "/webjars/**","/richtext/**" ,"/webfonts/**" );
	}

}
