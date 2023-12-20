package com.petitapetit.miml.domain.playlist.service;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.entity.TrackPlayList;
import com.petitapetit.miml.domain.playlist.mapper.PlayListMapper;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import com.petitapetit.miml.domain.playlist.repository.TrackPlayListRepository;
import com.petitapetit.miml.domain.track.entity.Track;
import com.petitapetit.miml.domain.track.repository.TrackRepository;
import com.petitapetit.miml.global.exception.BusinessException;
import com.petitapetit.miml.global.exception.CommonErrorCode;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final TrackRepository trackRepository;
    private final TrackPlayListRepository trackPlayListRepository;
    private final PlayListMapper playListMapper;

    public PlayListDto.SaveResponse savePlayList(PlayListDto.SaveRequest request, Long memberId) {

        PlayList playList = playListMapper.saveRequestToPlayList(request, memberId);

        PlayList savedPlayList = playListRepository.save(playList);

        return playListMapper.playListToSaveResponse(savedPlayList);
    }

    public PlayListDto.SaveResponse modifyPlayList(Long playListId, PlayListDto.SaveRequest request, Long memberId) {

        PlayList playList = getVerifiedPlaylist(playListId, memberId);

        PlayList modifiedPlayList = updatePlayListAttributes(request, playList);

        return playListMapper.playListToSaveResponse(modifiedPlayList);
    }

    private PlayList updatePlayListAttributes(PlayListDto.SaveRequest request, PlayList playList) {

        Optional.ofNullable(request.getIsPublic())
                .ifPresent(isPublic -> playList.updateIsPublic(request.getIsPublic()));

        Optional.ofNullable(request.getName())
                .ifPresent(name -> playList.updateName(request.getName()));

        return playList;
    }

    public PlayListDto.DetailResponse getPlayListById(Long playListId) {

        PlayList playList = checkExistence(playListId);

        return playListMapper.playListToDetailResponse(playList);
    }

    public List<PlayListDto.DetailResponse> getPlayLists(){

        List<PlayList> playLists = playListRepository.findAll();

        return playListMapper.playListsToDetailResponseLists(playLists);
    }

    public void deletePlayList(Long playListId, Long memberId){

        PlayList playList = getVerifiedPlaylist(playListId, memberId);

        playListRepository.delete(playList);
    }

    public PlayList getVerifiedPlaylist(Long playListId, Long memberId) {

        PlayList playList = checkExistence(playListId);
        checkAuthorization(memberId, playList);

        return playList;
    }

    private void checkAuthorization(Long memberId, PlayList playList) {

        if(!Objects.equals(playList.getMemberId(), memberId)){
            throw new BusinessException(CommonErrorCode.FORBIDDEN);
        }

    }

    private PlayList checkExistence(Long playListId) {

        return playListRepository.findById(playListId).orElseThrow(
                () -> new BusinessException(CommonErrorCode.PLAYLIST_NOT_FOUND));
    }

    @Transactional
    public void addTrackToPlayList(Long playListId, Long trackId, Long memberId) {
        PlayList playList = checkExistence(playListId);
        checkAuthorization(memberId, playList);
        Track track = trackRepository.findById(trackId)
                .orElseThrow(()-> new EntityNotFoundException());

        // 플레이리스트와 연결된 mapper를 가져와 track 추가
        Set<TrackPlayList> trackPlayLists = playList.getTrackPlayLists();
        TrackPlayList trackPlayList = new TrackPlayList(track, playList);
        trackPlayLists.add(trackPlayList);
        trackPlayListRepository.save(trackPlayList);
    }

    @Transactional
    public void removeTrackFromPlayList(Long playListId, Long trackId, Long memberId) {
        PlayList playList = checkExistence(playListId);
        checkAuthorization(memberId, playList);
        TrackPlayList trackPlayListToRemove = trackPlayListRepository.findByTrackIdAndPlayListPlayListId(trackId, playListId)
                .orElseThrow(()-> new EntityNotFoundException());

        // 찾은 엔티티를 삭제합니다.
        trackPlayListRepository.delete(trackPlayListToRemove);
    }
}
