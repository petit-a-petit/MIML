package com.petitapetit.miml.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2Provider;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderId(String providerId);

	Optional<Member> findByProviderAndProviderId(OAuth2Provider provider, String providerId);

}
