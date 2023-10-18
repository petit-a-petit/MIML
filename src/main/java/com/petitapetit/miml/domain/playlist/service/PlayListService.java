package com.petitapetit.miml.domain.playlist.service;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.mapper.PlayListMapperImpl;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final PlayListMapperImpl playListMapper;

    public PlayList savePlayList(PlayListDto.SaveRequest request, Long memberId) {

        PlayList playList = playListMapper.SaveRequestToPlayList(request, memberId);

        return playListRepository.save(playList);
    }
}
