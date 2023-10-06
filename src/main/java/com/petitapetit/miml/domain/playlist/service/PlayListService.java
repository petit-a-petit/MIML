package com.petitapetit.miml.domain.playlist.service;

import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlayListRepository playListRepository;

    public PlayList savePlayList(PlayList playList) {

        return playListRepository.save(playList);
    }
}
