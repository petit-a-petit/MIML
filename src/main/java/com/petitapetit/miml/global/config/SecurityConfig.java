package com.petitapetit.miml.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2UserService;
import com.petitapetit.miml.domain.auth.oauth.filter.OriginalUriFilter;
import com.petitapetit.miml.domain.auth.oauth.filter.SimpleAuthFilter;
import com.petitapetit.miml.domain.auth.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.petitapetit.miml.domain.auth.oauth.handler.OAuth2AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final CustomOAuth2UserService customOauth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin().disable()
			.httpBasic().disable()
			.csrf().disable()
			.headers().frameOptions().disable();

		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers("/").permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2Login -> oauth2Login
				.loginPage("/oauth2/authorization/spotify")
				.userInfoEndpoint(userInfoEndpoint ->
					userInfoEndpoint.userService(customOauth2UserService)
				)
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler)
			)
			.addFilterBefore(new OriginalUriFilter(), BasicAuthenticationFilter.class)
			.addFilterBefore(new SimpleAuthFilter(), OAuth2LoginAuthenticationFilter.class);

		return http.build();
	}
}

