package com.petitapetit.miml.domain.member.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.member.model.Member;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ArtistRepository artistRepository;
    private final MemberArtistRepository memberArtistRepository;

    public void likeArtist(Member member, Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(()->new NoSuchElementException());
        MemberArtist memberArtist = new MemberArtist(member,artist);
        member.likeArtist(memberArtist);
        memberArtistRepository.save(memberArtist);
    }

    public void unlikeArtist(Member member, Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NoSuchElementException());

        MemberArtist memberArtist = memberArtistRepository.findByMember_MemberIdAndArtist_Id(member.getMemberId(), artist.getId())
                .orElseThrow(() -> new NoSuchElementException("The member did not like this artist"));

        member.unlikeArtist(memberArtist);
        memberArtistRepository.deleteById(memberArtist.getId());
    }
}
