package com.petitapetit.miml.domain;

import com.google.gson.Gson;
import com.petitapetit.miml.domain.playlist.controller.PlayListController;
import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.service.PlayListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PlayListControllerTest {

    @InjectMocks
    private PlayListController playListController;

    @Mock
    private PlayListService playListService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(playListController).build();
    }

    @Test
    @DisplayName("POST 플레이리스트 등록 컨트롤러 로직 확인")
    public void savePlayList() throws Exception {
        //given

        PlayListDto.Post request = new PlayListDto.Post("플레이리스트 이름", true);


        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        ).andExpect(status().isOk());

    }
}
