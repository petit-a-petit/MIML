package com.petitapetit.miml.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// import com.petitapetit.miml.domain.auth.jwt.TokenProvider;
import com.petitapetit.miml.domain.auth.oauth.CustomOauth2UserService;
import com.petitapetit.miml.domain.auth.oauth.handler.CustomOAuth2AuthenticationSuccessHandler;
import com.petitapetit.miml.domain.auth.oauth.handler.OAuth2AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	// private final TokenProvider tokenProvider;
	private final CustomOauth2UserService customOauth2UserService;
	private final CustomOAuth2AuthenticationSuccessHandler customHandler;
	// private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
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
			.headers().frameOptions().disable()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers("/").permitAll()
				.antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2Login -> oauth2Login
				.loginPage("/auth/redirect-to-spotify")
				.userInfoEndpoint(userInfoEndpoint ->
					userInfoEndpoint.userService(customOauth2UserService)
				)
				.successHandler(customHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler)
			);
			// .logout(logout -> logout
			// 	.logoutUrl("/logout") // 로그아웃 URL 지정
			// 	.logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL 지정
			// )
			// .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

