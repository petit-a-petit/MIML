package com.petitapetit.miml.domain.track.repository;

import com.petitapetit.miml.domain.track.entity.ArtistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, Long> {
}
