package com.petitapetit.miml.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Set<TempUser> findByLikeArtistsSetContaining(TempArtist artist);
}
