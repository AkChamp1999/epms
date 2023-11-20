package com.epms.app.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

//    @SuppressWarnings("deprecation")
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeRequests(authorizeRequests -> authorizeRequests
//				.requestMatchers("/student-**").hasRole("STUDENT")
//				.requestMatchers("/teacher-**").hasRole("TEACHER")
//				.anyRequest().authenticated())
//				.formLogin(Customizer.withDefaults())
//				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
//				.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/403"));
//		return http.build();
//	}

}
