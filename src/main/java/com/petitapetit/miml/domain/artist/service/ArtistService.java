package com.petitapetit.miml.domain.artist.service;

import com.petitapetit.miml.domain.artist.Artist;
import com.petitapetit.miml.domain.artist.ArtistRepository;
import com.petitapetit.miml.domain.artist.MemberArtist;
import com.petitapetit.miml.domain.artist.MemberArtistRepository;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
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
}

