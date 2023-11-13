package com.petitapetit.miml.domain.friendship.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.petitapetit.miml.domain.member.model.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "friendship_tb")
public class Friendship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long friendshipId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_member_id")
	private Member fromMember; // 친구 요청을 보낸 Member의 ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_member_id")
	private Member toMember; // 친구 요청을 받은 Member의 ID
	private Boolean isFriend; // 서로 친구인지 판단
	@CreationTimestamp
	private LocalDateTime createdAt;

	public void acceptRequest() {
		isFriend = Boolean.TRUE;
	}
}
