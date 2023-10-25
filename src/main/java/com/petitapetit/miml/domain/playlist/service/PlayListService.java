package com.petitapetit.miml.domain.playlist.service;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.mapper.PlayListMapper;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import com.petitapetit.miml.global.exception.CommonErrorCode;
import com.petitapetit.miml.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public PlayListDto.SaveResponse modifyPlayList(Long playListId, PlayListDto.SaveRequest request, Long memberId) {
        PlayList playList = getVerifiedPlaylist(playListId, memberId);

        if (request.getIsPublic() != null) {
            playList.setIsPublic(request.getIsPublic());
        }

        if(request.getName() != null){
            playList.setName(request.getName());
        }

        PlayList modifiedPlayList = playListRepository.save(playList);

        return playListMapper.PlayListToSaveResponse(modifiedPlayList);
    }

    public PlayListDto.Response getPlayListById(Long playListId) {

        PlayList playList = checkExistence(playListId);

        return playListMapper.PlayListToResponse(playList);
    }

    public List<PlayListDto.Response> getPlayLists(){

        List<PlayList> playLists = playListRepository.findAll();

        return playListMapper.PlayListsToResponse(playLists);
    }

    public void deletePlayList(Long playListId, Long memberId){

        PlayList playList = getVerifiedPlaylist(playListId, memberId);

        playListRepository.delete(playList);
    }

    @Transactional
    public PlayList getVerifiedPlaylist(Long playListId, Long memberId) {

        PlayList playList = checkExistence(playListId);
        checkAuthorization(memberId, playList);

        return playList;
    }

    private void checkAuthorization(Long memberId, PlayList playList) {
        if(!Objects.equals(playList.getPlayListId(), memberId)){
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND); //추후 상태 코드 결정
        }
    }

    private PlayList checkExistence(Long PlayListId) {
        Optional<PlayList> optional = playListRepository.findById(PlayListId);
        return optional.orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)); //추후 상태 코드 결정
    }
}
