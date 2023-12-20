package com.petitapetit.miml.domain.artist.api;

import com.petitapetit.miml.domain.artist.dto.ArtistDto.LikeResponse;
import com.petitapetit.miml.domain.artist.service.ArtistService;
import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    // 아티스트 좋아요
    @PostMapping("/like/{artistId}")
    public ResponseEntity<Void> likeArtist(
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomOAuth2User oAuth2User
    ) {
        artistService.likeArtist(oAuth2User.getUser().getMemberId(), artistId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 사용자가 좋아요한 아티스트 목록 조회
    @GetMapping("/liked")
    public ResponseEntity<List<LikeResponse>> getLikedArtistList(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User
    ) {
        List<LikeResponse> likedArtists = artistService.getLikedArtists(oAuth2User.getUser().getMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(likedArtists);
    }

    // 아티스트 좋아요 취소
    @DeleteMapping("/like/{artistId}")
    public ResponseEntity<Void> cancelLikeArtist(
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomOAuth2User oAuth2User
    ) {
        artistService.cancelLikeArtist(oAuth2User.getUser().getMemberId(), artistId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
