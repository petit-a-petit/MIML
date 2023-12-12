package com.petitapetit.miml.domain.artist.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistServiceIntegrationTest {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MemberArtistRepository memberArtistRepository;

    @Test
    @DisplayName("유저 아티스트 좋아요 기능 저장 확인 테스트")
    void testLikeArtist() {
        // Given
        Member member = new Member();
        Artist artist = new Artist();
        member = memberRepository.save(member);
        artist = artistRepository.save(artist);

        // When
        artistService.likeArtist(member.getMemberId(), artist.getId());

        // Then
        Optional<MemberArtist> optionalMemberArtist = memberArtistRepository.findByMemberMemberIdAndArtistId(member.getMemberId(), artist.getId());
        assertTrue(optionalMemberArtist.isPresent());
    }

    @Test
    @DisplayName("유저 아티스트 좋아요 삭제 기능 저장 확인 테스트")
    void testCancelLikeArtist() {
        // Given
        Member member = new Member();
        Artist artist = new Artist();
        member = memberRepository.save(member);
        artist = artistRepository.save(artist);
        MemberArtist memberArtist = MemberArtist.likeArtist(member, artist);
        memberArtistRepository.save(memberArtist);

        // When
        artistService.cancelLikeArtist(member.getMemberId(), artist.getId());

        // Then
        Optional<MemberArtist> optionalMemberArtist = memberArtistRepository.findByMemberMemberIdAndArtistId(member.getMemberId(), artist.getId());
        assertFalse(optionalMemberArtist.isPresent());
    }
}
