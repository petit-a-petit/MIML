package com.petitapetit.miml.domain.playlist.controller;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping
    public ResponseEntity postPlayList(@RequestBody PlayListDto.SaveRequest request){

        Long memberId = 1L; //mock
        playListService.savePlayList(request , memberId);

        return ResponseEntity.ok().build();
    }
}
