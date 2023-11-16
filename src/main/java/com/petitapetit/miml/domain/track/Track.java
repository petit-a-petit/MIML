package com.petitapetit.miml.domain.track;

import java.util.ArrayList;
import java.util.List;
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
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;
    private String uri;
    private String name;
    private String source;

    @OneToMany(mappedBy = "track")
    private List<ArtistTrack> artistTracks = new ArrayList<>();

    // getters, setters, equals, hashCode 생략
    public Track(TrackDto trackDto) {
        this.rank = trackDto.getRank();
        this.uri = trackDto.getUri();
        this.name = trackDto.getTrackName();
        this.source = trackDto.getSource();
    }

    public void setArtistTracks(List<ArtistTrack> artistTracks) {
        this.artistTracks = artistTracks;
    }
}
