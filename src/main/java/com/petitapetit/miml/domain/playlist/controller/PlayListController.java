package com.petitapetit.miml.domain.playlist.controller;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
public class PlayListController {

    @PostMapping
    public ResponseEntity postPlayList(@RequestBody PlayListDto.Post requestBody){

        return ResponseEntity.ok().build();
    }
}
