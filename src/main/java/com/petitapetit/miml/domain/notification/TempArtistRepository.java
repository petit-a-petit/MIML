package com.petitapetit.miml.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempArtistRepository extends JpaRepository<TempArtist, Long> {
    TempArtist findByName(String name);
}
