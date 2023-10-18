package com.petitapetit.miml.domain.playlist.mapper;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayListMapper {
    default PlayList SaveRequestToPlayList(PlayListDto.SaveRequest saveRequest, Long memberId){

        return PlayList.builder()
                .name(saveRequest.getName())
                .memberId(memberId)
                .isPublic(saveRequest.getIsPublic()).build();
    }

    PlayListDto.SaveResponse PlayListToSaveResponse(PlayList savedPlayList);
}
