package com.petitapetit.miml.domain.artist.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.artist.dto.ArtistDto.LikeResponse;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
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
        Member user = getMember(userId);
        Artist artist = getArtist(artistId);

        MemberArtist userArtist = MemberArtist.likeArtist(user, artist);
        memberArtistRepository.save(userArtist);
    }

    private Artist getArtist(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("아티스트를 찾을 수 없습니다: " + artistId));
    }

    private Member getMember(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + userId));
    }

    @Transactional(readOnly = true)
    public List<LikeResponse> getLikedArtists(Long userId) {
        Member user = getMember(userId);

        return user.getLikedArtists().stream()
                .map(userArtist -> new LikeResponse(
                        userArtist.getArtist().getId(),
                        userArtist.getArtist().getName()
                )).collect(Collectors.toList());
    }

    @Transactional
    public void cancelLikeArtist(Long userId, Long artistId) {
        MemberArtist userArtist = getMemberArtist(userId, artistId);

        userArtist.cancelLike();
        memberArtistRepository.delete(userArtist);
    }

    private MemberArtist getMemberArtist(Long userId, Long artistId) {
        return memberArtistRepository.findByMemberMemberIdAndArtistId(userId, artistId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 또는 아티스트를 찾을 수 없습니다."));
    }

    public Set<Member> findMembersByLikedArtistNames(List<Artist> artists) {
        return memberRepository.findByLikedArtistNames(artists.stream().map(Artist::getName).collect(
                Collectors.toList()));
    }
}

