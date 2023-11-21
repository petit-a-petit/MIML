package com.petitapetit.miml.domain.artist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import com.petitapetit.miml.test.ServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ArtistServiceTest extends ServiceTest{

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private MemberArtistRepository memberArtistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    @DisplayName("사용자가 아티스트 추가 시 정보가 저장된다.")
    void testLikeArtist() {
        // Given
        Member member = new Member();
        Artist artist = new Artist();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(artistRepository.findById(anyLong())).thenReturn(Optional.of(artist));

        // When
        artistService.likeArtist(1L, 1L);

        // Then
        verify(memberArtistRepository, times(1)).save(any(MemberArtist.class));
    }

    @Test
    @DisplayName("사용자가 좋아요한 아티스트들을 확인할 수 있다.")
    void testGetLikedArtists() {
        // Given
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // When
        artistService.getLikedArtists(1L);

        // Then
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("사용자는 좋아요한 아티스트를 취소할 수 있다.")
    void testCancelLikeArtist() {
        // Given
        MemberArtist memberArtist = new MemberArtist(new Member(), new Artist());
        when(memberArtistRepository.findByMember_MemberIdAndArtist_Id(anyLong(), anyLong())).thenReturn(Optional.of(memberArtist));

        // When
        artistService.cancelLikeArtist(1L, 1L);

        // Then
        verify(memberArtistRepository, times(1)).delete(any(MemberArtist.class));
    }
}
