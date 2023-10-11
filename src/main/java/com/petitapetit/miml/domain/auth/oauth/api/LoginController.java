package com.petitapetit.miml.domain.auth.oauth.api;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	@GetMapping("/redirect-to-spotify")
	public RedirectView redirectToSpotify() {
		return new RedirectView("/oauth2/authorization/spotify");
	}

	@GetMapping("/success-login")
	public String successLogin(@PathParam("token") String token) {
		return token;
	}
}
