package com.petitapetit.miml.domain.friendship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petitapetit.miml.domain.friendship.model.Friendship;
import com.petitapetit.miml.domain.member.model.Member;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	List<Friendship> findFriendshipsByFromMemberAndIsFriend(Member toMember, Boolean isFriend);
}
