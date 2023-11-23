package com.petitapetit.miml.domain.track.entity;

import com.petitapetit.miml.domain.artist.domain.Artist;
import javax.persistence.Entity;
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
public class ArtistTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    public ArtistTrack(Artist artist) {
        this.artist = artist;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
