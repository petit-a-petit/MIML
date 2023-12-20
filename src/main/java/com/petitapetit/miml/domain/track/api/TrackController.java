package com.petitapetit.miml.domain.track.api;

import com.petitapetit.miml.domain.track.dto.TrackInfo;
import com.petitapetit.miml.domain.track.service.TrackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    /**
     * 노래 조회
     */
    @GetMapping
    public ResponseEntity<List<TrackInfo>> getTracks(Pageable pageable) {
        List<TrackInfo> trackInfoList = trackService.getTrackPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(trackInfoList);
    }
}
