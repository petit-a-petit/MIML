package com.petitapetit.miml.domain.playlist;

import com.google.gson.Gson;
import com.petitapetit.miml.domain.playlist.controller.PlayListController;
import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(playListController).build();
    }

    @Test
    @DisplayName("컨트롤러 플레이리스트 등록 로직 확인")
    public void postPlayList() throws Exception {
        //given

        PlayListDto.SaveRequest request = new PlayListDto.SaveRequest("플레이리스트 이름", true);


        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        ).andExpect(status().isOk());

    }
}
