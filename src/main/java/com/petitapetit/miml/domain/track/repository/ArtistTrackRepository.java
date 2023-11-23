package com.petitapetit.miml.domain.track.repository;

import com.petitapetit.miml.domain.track.entity.ArtistTrack;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, Long> {
    @Query("select at from ArtistTrack at join fetch at.artist where at.track = :track")
    List<ArtistTrack> findAllByTrack(Track track);
}
