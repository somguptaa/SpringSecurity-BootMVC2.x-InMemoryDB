package com.boot.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())   //using BCrypt encoder for password security instead of {noop}
			.withUser("som").password("$2a$10$HdiYik9N/S.GsTOZnlaAVelq8BRfMsteMzp3Clf4EVYMGu8eMbbgO").roles("USER");   //creating user with username som, BCrypt encoded password for "gupta", and role USER
		
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())   //using BCrypt encoder for second user
			.withUser("zakir").password("$2a$10$XCnGIGDSdnDLZNUv6SYH/OAnS0of7mcm2JYZp0O0vCmRV1WV1OWU6").roles("MANAGER");  //creating user with username zakir, BCrypt encoded password for "hyd", and role MANAGER
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/denied").permitAll()   //this is open for all users, any user can access these pages "/" is home page and "/denied" is for access deny page
			
			.antMatchers("/offers").authenticated()   //only registered users can access this page, if not logged in it will redirect to login page
			
			.antMatchers("/checkBalance").hasAnyRole("USER", "MANAGER")   //only users with USER or MANAGER role can access check balance page
			
			.antMatchers("/approveloan").hasRole("MANAGER")   //only MANAGER can access the approve loan page, if USER tries to access it will show denied page
			
			.anyRequest().authenticated()   //all other requests need authentication
			
			.and()
			
			// .httpBasic()   //it will show browser pop-up for username and password, we cannot change the design, if we want custom page we have to use formLogin()
			
			.formLogin()   //it will show a login page for username and password, Spring provides default page but we can customize it by passing our own page
			
			.and()
			.rememberMe()   //by using this we can login and check remember me checkbox, if we close the browser without logout and reopen it will not ask password again
						    //it uses persistent cookie for storing username and password for 48 hours
			
			.and()
			.logout()   //this provides default logout functionality
						//we need to add logout link in our jsp page like (<a href="logout">Logout</a>) it will perform logout
			
			//.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))   //this is for custom logout URL, instead of /logout we can use /signout
			
			.and()
			.exceptionHandling().accessDeniedPage("/denied")   //it will handle 403 exception, if user doesn't have access it will redirect to denied page
																//if we don't add this it will show default 403 error page
			
			.and()
			.sessionManagement()
				.maximumSessions(2)   //maximum 2 users can login with same credentials at the same time
				.maxSessionsPreventsLogin(true);   //if true, 3rd login will be blocked, if false oldest session will be invalidated and new login will be allowed
	}
}

/*
 * 						BCrypt Password Encoding
 * 					  ============================
 * 
 * Why BCrypt?
 * - Secure password hashing algorithm
 * - Cannot be reversed to get original password
 * - Each time we encode same password, we get different hash (because of salt)
 * - Production-ready and recommended by Spring Security
 * 
 * How to generate BCrypt password:
 * 
 * Method 1: Using Java code
 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
 * String encodedPassword = encoder.encode("gupta");
 * System.out.println(encodedPassword);
 * 
 * Method 2: Using online tool
 * - Go to https://bcrypt-generator.com/
 * - Enter password and click generate
 * 
 * Password mappings in this config:
 * som   -> "gupta" -> $2a$10$HdiYik9N/S.GsTOZnlaAVelq8BRfMsteMzp3Clf4EVYMGu8eMbbgO
 * zakir -> "hyd"   -> $2a$10$XCnGIGDSdnDLZNUv6SYH/OAnS0of7mcm2JYZp0O0vCmRV1WV1OWU6
 * 
 * Note: Users will still type "gupta" and "hyd" to login, Spring Security will encode and compare
 * 
 */

/*
 * 						Internal of Session Management
 * 					======================================
 * 
 * If Login is successful it creates new Session:
 * 
 * HttpSession session = request.getSession(); 
 * or 
 * HttpSession session = request.getSession(true);
 * 
 * To access the existing session:
 * HttpSession session = request.getSession(false);
 * 
 * To stop/invalidate the Session:
 * session.invalidate();
 * 
 * To specify max inactive interval period for a Session:
 * session.setMaxInactiveInterval(1200);  //20 mins (time is in seconds)
 * 
 * //default session timeout is 30 mins
 * 
 */