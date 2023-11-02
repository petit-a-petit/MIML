package com.petitapetit.miml.domain.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petitapetit.miml.domain.friendship.model.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
