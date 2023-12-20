// package com.petitapetit.miml.domain.playlist;
//
// import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
// import com.petitapetit.miml.domain.playlist.entity.PlayList;
// import com.petitapetit.miml.domain.playlist.mapper.PlayListMapper;
// import com.petitapetit.miml.domain.playlist.repository.PlayListRepository;
// import com.petitapetit.miml.domain.playlist.service.PlayListService;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static org.mockito.Mockito.doReturn;
//
// @ExtendWith(MockitoExtension.class)
// public class PlayListServiceTest {
//
//     @InjectMocks
//     private PlayListService playListService;
//
//     @Mock
//     private PlayListMapper playListMapper;
//
//     @Mock
//     private PlayListRepository playListRepository;
//
//     @DisplayName("서비스 플레이리스트 등록 로직 확인")
//     @Test
//     public void savePlayList(){
//         //given
//         PlayListDto.SaveRequest saveRequest = PlayListDto.SaveRequest.builder()
//                 .name("플레이리스트 이름")
//                 .isPublic(true)
//                 .build();
//
//         PlayList playList = PlayList.builder()
//                 .playListId(1L)
//                 .name("플레이리스트 이름")
//                 .isPublic(true)
//                 .memberId(1L)
//                 .build();
//
//         PlayListDto.SaveResponse saveResponse = PlayListDto.SaveResponse.builder()
//                         .playListId(1L).build();
//
//
//         doReturn(playList).when(playListMapper).SaveRequestToPlayList(saveRequest, 1L);
//         doReturn(playList).when(playListRepository).save(playList);
//         doReturn(saveResponse).when(playListMapper).PlayListToSaveResponse(playList);
//
//
//         //when
//         PlayListDto.SaveResponse savedPlayList = playListService.savePlayList(saveRequest, 1L);
//
//         //then
//         assertThat(savedPlayList.getPlayListId()).isEqualTo(1L);
//
//     }
// }