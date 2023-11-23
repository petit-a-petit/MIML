package com.petitapetit.miml.domain.member.repository;

import java.util.List;
import java.util.Optional;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderAndProviderId(OAuth2Provider oAuth2Provider, String providerId);
	@Query("select m from Member m join m.likedArtists ma where ma.artist.name in :artistNames")
	Set<Member> findByLikedArtistNames(@Param("artistNames") List<String> artistNames);
}
