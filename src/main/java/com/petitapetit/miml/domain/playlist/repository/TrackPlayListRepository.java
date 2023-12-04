package com.petitapetit.miml.domain.playlist.repository;

import com.petitapetit.miml.domain.playlist.entity.TrackPlayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackPlayListRepository extends JpaRepository<TrackPlayList, Long> {
    Optional<TrackPlayList> findByTrackIdAndPlayList_PlayListId(Long trackId, Long playListId);
}
