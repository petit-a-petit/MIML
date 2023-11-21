package com.petitapetit.miml.domain.artist.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.artist.dto.ArtistDto;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final MemberRepository memberRepository;

    private final ArtistRepository artistRepository;

    private final MemberArtistRepository memberArtistRepository;

    @Transactional
    public void likeArtist(Long userId, Long artistId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트를 찾을 수 없습니다: " + artistId));

        MemberArtist userArtist = new MemberArtist(user, artist);
        user.likeArtist(userArtist);
        artist.likedByUser(userArtist);
        memberArtistRepository.save(userArtist);
    }

    @Transactional(readOnly = true)
    public List<ArtistDto.Response> getLikedArtists(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        return user.getLikedArtists().stream()
                .map(userArtist -> new ArtistDto.Response(
                        userArtist.getArtist().getId(),
                        userArtist.getArtist().getName()
                )).collect(Collectors.toList());
    }
}

