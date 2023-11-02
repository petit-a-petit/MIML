package com.petitapetit.miml.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderAndProviderId(OAuth2Provider oAuth2Provider, String providerId);
}
