package com.petitapetit.miml.domain.member.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberId implements Serializable {
	@Enumerated(EnumType.STRING)
	private OAuth2Provider provider;
	private String providerId;
}
