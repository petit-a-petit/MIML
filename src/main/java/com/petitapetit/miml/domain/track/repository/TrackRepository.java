package com.petitapetit.miml.domain.track.repository;

import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByName(String name);
}
