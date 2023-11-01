package com.petitapetit.miml.domain.member.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2Provider;
import com.petitapetit.miml.domain.friendship.model.Friendship;

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
	@OneToMany(mappedBy = "friendshipId", cascade = CascadeType.ALL)
	private List<Friendship> friendships = new ArrayList<>(); // 요청 받은 리스트
}
