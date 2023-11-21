package com.petitapetit.miml.domain.playlist.controller;

import com.petitapetit.miml.domain.playlist.dto.PlayListDto;
import com.petitapetit.miml.domain.playlist.service.PlayListService;
import com.petitapetit.miml.util.UriUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;
    public static final String DEFAULT_URI = "/playlists";


    @PostMapping
    public ResponseEntity<PlayListDto.SaveResponse> postPlayList(@RequestBody PlayListDto.SaveRequest request){

        Long memberId = 1L; //mock
        PlayListDto.SaveResponse response = playListService.savePlayList(request , memberId);

        URI uri = UriUtil.createUri(DEFAULT_URI, response.getPlayListId());

        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{play_list_id}")
    public ResponseEntity<PlayListDto.SaveResponse> patchPlayList(@PathVariable("play_list_id") Long playListId,
                                                                  @RequestBody PlayListDto.SaveRequest request){

        Long memberId = 1L;//mock
        PlayListDto.SaveResponse response = playListService.modifyPlayList(playListId, request, memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{play_list_id}")
    public ResponseEntity<PlayListDto.DetailResponse> getPlayListDetail(@PathVariable("play_list_id") Long playListId){

        PlayListDto.DetailResponse response = playListService.getPlayListById(playListId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PlayListDto.DetailResponse>> getPlayLists(){

        List<PlayListDto.DetailResponse> response = playListService.getPlayLists();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{play_list_id}")
    public ResponseEntity<String> deletePlayList(@PathVariable("play_list_id") Long playListId){

        Long memberId = 1L;
        playListService.deletePlayList(playListId, memberId);

        return ResponseEntity.noContent().build();
    }
}
