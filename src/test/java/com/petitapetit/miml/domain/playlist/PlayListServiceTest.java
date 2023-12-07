package com.petitapetit.miml.domain.playlist;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.entity.TrackPlayList;
import com.petitapetit.miml.domain.playlist.mapper.PlayListMapper;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import com.petitapetit.miml.domain.playlist.repository.TrackPlayListRepository;
import com.petitapetit.miml.domain.playlist.service.PlayListService;
import com.petitapetit.miml.domain.track.entity.Track;
import com.petitapetit.miml.domain.track.repository.TrackRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayListServiceTest {

    @InjectMocks
    private PlayListService playListService;

    @Mock
    private PlayListMapper playListMapper;

    @Mock
    private PlayListRepository playListRepository;
    @Mock
    private TrackRepository trackRepository;
    @Mock
    private TrackPlayListRepository trackPlayListRepository;

    @DisplayName("서비스 플레이리스트 등록 로직 확인")
    @Test
    void savePlayList(){
        //given
        PlayListDto.SaveRequest saveRequest = PlayListDto.SaveRequest.builder()
                .name("플레이리스트 이름")
                .isPublic(true)
                .build();

        PlayList playList = PlayList.builder()
                .playListId(1L)
                .name("플레이리스트 이름")
                .isPublic(true)
                .memberId(1L)
                .build();

        PlayListDto.SaveResponse saveResponse = PlayListDto.SaveResponse.builder()
                        .playListId(1L).build();


        doReturn(playList).when(playListMapper).saveRequestToPlayList(saveRequest, 1L);
        doReturn(playList).when(playListRepository).save(playList);
        doReturn(saveResponse).when(playListMapper).playListToSaveResponse(playList);


        //when
        PlayListDto.SaveResponse savedPlayList = playListService.savePlayList(saveRequest, 1L);

        //then
        assertThat(savedPlayList.getPlayListId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("플레이리스트에 음악 추가 성공")
    void addTrackToPlayList() {
        PlayList playList = PlayList.builder()
                .playListId(1L)
                .memberId(1L)
                .name("playList")
                .isPublic(true)
                .build();

        Track track = new Track(1L,1,"url","title","source",null,null);

        when(playListRepository.findById(anyLong())).thenReturn(Optional.of(playList));
        when(trackRepository.findById(anyLong())).thenReturn(Optional.of(track));

        playListService.addTrackToPlayList(1L, 1L, 1L);

        verify(trackPlayListRepository, times(1)).save(any(TrackPlayList.class));
    }

    @Test
    @DisplayName("플레이리스트에서 음악 제거 성공")
    void removeTrackFromPlayList() {
        PlayList playList = PlayList.builder()
                .playListId(1L)
                .memberId(1L)
                .name("playList")
                .isPublic(true)
                .build();

        Track track = new Track(1L,1,"url","title","source",null,null);
        TrackPlayList trackPlayList = new TrackPlayList(1L,track,playList);

        when(playListRepository.findById(anyLong())).thenReturn(Optional.of(playList));
        when(trackPlayListRepository.findByTrackIdAndPlayListPlayListId(anyLong(), anyLong())).thenReturn(Optional.of(trackPlayList));

        playListService.removeTrackFromPlayList(1L, 1L, 1L);

        verify(trackPlayListRepository, times(1)).delete(trackPlayList);
    }
}