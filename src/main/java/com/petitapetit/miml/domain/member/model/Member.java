package com.petitapetit.miml.domain.member.model;

import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member_tb")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	private String name;
	private String email;
	private String image;
	@Enumerated(EnumType.STRING)
	private RoleType role;
	@Enumerated(EnumType.STRING)
	private OAuth2Provider provider;
	private String providerId;
	@OneToMany(mappedBy = "member")
	@Builder.Default
	private Set<MemberArtist> likedArtists = new HashSet<>();
}
