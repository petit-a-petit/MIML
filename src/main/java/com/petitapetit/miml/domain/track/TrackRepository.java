package com.petitapetit.miml.domain.track;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByName(String name);
}
