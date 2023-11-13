package com.petitapetit.miml.domain.auth.oauth.userinfo;

import java.util.Map;

import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;

public class SpotifyUserInfo extends OAuth2UserInfo {

	public SpotifyUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public OAuth2Provider getProvider() {
		return OAuth2Provider.SPOTIFY;
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("display_name");
	}

	@Override
	public String getImage() {
		return (String) attributes.get("image_url");
	}
}
