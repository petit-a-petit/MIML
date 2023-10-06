package com.petitapetit.miml.domain.playlist;

import com.petitapetit.miml.domain.playlist.entity.PlayList;
import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
import com.petitapetit.miml.domain.playlist.service.PlayListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PlayListServiceTest {

    @InjectMocks
    private PlayListService playListService;

    @Mock
    private PlayListRepository playListRepository;

    @DisplayName("서비스 플레이리스트 등록 로직 확인")
    @Test
    public void savePlayList(){
        //given
        PlayList request = PlayList.builder()
                .playListId(1L)
                .name("플레이리스트 이름")
                .memberId(1L)
                .publicYN(true).build();

        doReturn(new PlayList(1L, "플레이리스트 이름", 1L, true)).when(playListRepository).save(request);

        //when
        PlayList savedPlayList = playListService.savePlayList(request);

        //then
        assertThat(savedPlayList.getPlayListId()).isEqualTo(request.getPlayListId());
        assertThat(savedPlayList.getName()).isEqualTo("플레이리스트 이름");
        assertThat(savedPlayList.getMemberId()).isEqualTo(1L);
        assertThat(savedPlayList.isPublicYN()).isTrue();
    }
}
