package com.petitapetit.miml.domain.member.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.domain.MemberArtist;
import com.petitapetit.miml.domain.artist.domain.MemberArtistRepository;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
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
}
