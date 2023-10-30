package com.petitapetit.miml.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempSongRepository extends JpaRepository<TempSong, Long> {
    List<TempSong> findByArtistName(String artistName);
}
