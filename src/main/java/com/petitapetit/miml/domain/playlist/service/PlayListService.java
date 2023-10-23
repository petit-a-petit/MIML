package com.petitapetit.miml.domain.playlist.service;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.mapper.PlayListMapper;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final PlayListMapper playListMapper;

    public PlayListDto.SaveResponse savePlayList(PlayListDto.SaveRequest request, Long memberId) {

        PlayList playList = playListMapper.SaveRequestToPlayList(request, memberId);

        PlayList savedPlayList = playListRepository.save(playList);

        return playListMapper.PlayListToSaveResponse(savedPlayList);
    }
}

