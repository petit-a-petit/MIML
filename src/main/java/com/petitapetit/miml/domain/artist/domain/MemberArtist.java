package com.petitapetit.miml.domain.artist.domain;

import com.petitapetit.miml.domain.member.model.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private MemberArtist(Member member, Artist artist) {
        this.member = member;
        this.artist = artist;
    }

    public static MemberArtist likeArtist(Member member, Artist artist) {
        MemberArtist memberArtist = new MemberArtist(member, artist);

        member.getLikedArtists().add(memberArtist);
        artist.getLikedByUsers().add(memberArtist);

        return memberArtist;
    }
    public void cancelLike() {
        this.member.getLikedArtists().remove(this);
        this.artist.getLikedByUsers().remove(this);
        this.member = null;
        this.artist = null;
    }
}
