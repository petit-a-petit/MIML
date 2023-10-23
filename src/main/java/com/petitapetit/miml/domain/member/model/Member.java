package com.petitapetit.miml.domain.member.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

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

	@EmbeddedId
	private MemberId id;

	private String name;
	private String email;
	private String image;
	@Enumerated(EnumType.STRING)
	private RoleType role;
}
