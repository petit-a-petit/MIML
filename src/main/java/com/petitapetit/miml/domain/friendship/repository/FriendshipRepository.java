package com.petitapetit.miml.domain.friendship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petitapetit.miml.domain.friendship.model.Friendship;
import com.petitapetit.miml.domain.member.model.Member;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	List<Friendship> findFriendshipsByFromMemberAndIsFriend(Member fromMember, Boolean isFriend);
	List<Friendship> findFriendshipsByToMemberAndIsFriend(Member toMember, Boolean isFriend);
	@Query(
		"SELECT f.toMember FROM Friendship f " +
			"JOIN Friendship sf " +
			"ON f.toMember.memberId = sf.fromMember.memberId " +
			"WHERE f.fromMember = :loginMember " +
			"AND f.fromMember = sf.toMember " +
			"AND f.isFriend = true " +
			"AND sf.isFriend = true"
	)
	List<Member> findFriends(Member loginMember);
	Optional<Friendship> findByFromMemberAndToMemberAndIsFriend(Member fromMember, Member toMember, Boolean isFriend);
}
