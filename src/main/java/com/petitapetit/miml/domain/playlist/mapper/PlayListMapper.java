package com.petitapetit.miml.domain.playlist.mapper;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayListMapper {
    default PlayList saveRequestToPlayList(PlayListDto.SaveRequest saveRequest, Long memberId){

        return PlayList.builder()
                .name(saveRequest.getName())
                .memberId(memberId)
                .isPublic(saveRequest.getIsPublic()).build();
    }

    PlayListDto.SaveResponse playListToSaveResponse(PlayList savedPlayList);

    PlayListDto.Response playListToResponse(PlayList playList);

    List<PlayListDto.Response> playListsToResponse(List<PlayList> playLists);
}
