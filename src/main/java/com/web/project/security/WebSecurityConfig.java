package com.web.project.security;

import com.web.project.security.oauth.CustomerOauth2UserService;
import com.web.project.security.oauth.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired private CustomerOauth2UserService customerOauth2UserService;

	@Autowired private Oauth2LoginSuccessHandler loginSuccessHandler;
	@Autowired private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
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
			    .requestMatchers("/nhanvien/**" , "/loaisanpham/**" , "/sanpham/**" ,
						"/hoadon/**" , "/doanhthu/**" , "/account/**" , "/cart/**", "/review/**" , "/setting/**" ,
						"/account/**" ,"/history/**" )
			   .authenticated() // Tất cả các địa chỉ trên yêu cầu user đăng nhập thành công vào hệ thống
			    .anyRequest()
			    .permitAll() // các địa chỉ còn lại khác với các địa chỉ trên, sẽ được cho phép truy cập
			    .and()
				.formLogin() // Cho phép người dùng xác thực bằng form login
			   .loginPage("/login") // custom địa chỉ login (ví dụ có thể chuyển thành /customLogin)
			    .usernameParameter("email") // sử dụng field `email` để kiểm tra người dùng có trong hệ thống không
			    .successHandler(databaseLoginSuccessHandler) // custom khi đăng nhập thành công sẽ làm gì đó
			    .permitAll() // Tất cả đều được truy cập vào địa chỉ này
			    .and()
			    .oauth2Login() // Cho phép người dùng xác thực bằng (Facebook, Google, Git, ... )
			   	.loginPage("/login") // custom địa chỉ login (ví dụ có thể chuyển thành /customLogin)
			   	.userInfoEndpoint()
			   	.userService(customerOauth2UserService) // khi đăng nhập thì Spring security sẽ lấy thông tin của
			   											// customerOauth2UserService để kiểm tra
			    .and()
			    .successHandler(loginSuccessHandler) // giống với custom khi đặng nhập thành công trên
			   	.and()
				.logout()// logout sễ cho phép tất cả
			   .permitAll().and()
				.rememberMe().key("AbcDefgHijKlmnOpqrs_1234567890") // sử dùng rememberMe để khi phần mềm restart
			   												// hoặc out ra khỏi trang web
			   												// nó sẽ mặc định nhớ thông tin user đã đăng nhập

				.tokenValiditySeconds(7 * 24 * 60 * 60) // sẽ hết hạn theo milisecond ~ 7 ngày
			   	.and()
			   	.build();
	}
//	 @Bean
//	 WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**" , "/webjars/**","/webfonts/**" );
//	}
}
