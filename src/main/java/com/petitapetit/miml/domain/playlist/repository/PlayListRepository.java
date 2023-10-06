package com.petitapetit.miml.domain.playlist.repository;

import com.petitapetit.miml.domain.playlist.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
}
