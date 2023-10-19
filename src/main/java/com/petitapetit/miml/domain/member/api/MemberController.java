package com.petitapetit.miml.domain.member.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;

@Controller
public class MemberController {

	// @Value("${spotify.api.url}")
	private String spotifyApiUrl = "https://api.spotify.com"; // Spotify API 엔드포인트 URL

	@GetMapping("/me")
	public ResponseEntity<String> getMySpotifyData(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {

		String apiUrl = spotifyApiUrl + "/v1/me";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oAuth2User.getAccessToken());
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			apiUrl,
			HttpMethod.GET,
			httpEntity,
			String.class
		);
		String spotifyData = response.getBody();

		return ResponseEntity.ok(spotifyData);
	}
}
