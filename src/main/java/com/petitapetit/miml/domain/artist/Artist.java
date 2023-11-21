package com.petitapetit.miml.domain.artist;

import com.petitapetit.miml.domain.track.ArtistTrack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "artist")
    private List<ArtistTrack> artistTracks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private Set<MemberArtist> likedByUsers = new HashSet<>();

    public Artist(String name) {
        this.name = name;
    }

    public void likedByUser(MemberArtist memberArtist) {
        likedByUsers.add(memberArtist);
        memberArtist.setArtist(this);
    }
}
