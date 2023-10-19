package com.petitapetit.miml.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.petitapetit.miml.domain.auth.oauth.CustomOauth2UserService;
import com.petitapetit.miml.domain.auth.oauth.filter.OriginalUriFilter;
import com.petitapetit.miml.domain.auth.oauth.handler.CustomOAuth2AuthenticationSuccessHandler;
import com.petitapetit.miml.domain.auth.oauth.handler.OAuth2AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	// private final TokenProvider tokenProvider;
	private final CustomOauth2UserService customOauth2UserService;
	private final CustomOAuth2AuthenticationSuccessHandler customHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public RequestCache requestCache() {
		return new HttpSessionRequestCache();
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
				.successHandler(customHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler)
			)
			.addFilterBefore(new OriginalUriFilter(), BasicAuthenticationFilter.class);

		return http.build();
	}
}

