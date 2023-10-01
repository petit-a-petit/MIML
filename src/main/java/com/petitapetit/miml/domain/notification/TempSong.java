package com.petitapetit.miml.domain.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TempSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    // 한 아티스트는 여러 개의 노래를 가질 수 있음.
    @JoinColumn(name = "temp_artist_id")
    private TempArtist artist;

    public TempSong(String name, TempArtist artist) {
        this.name = name;
        this.artist = artist;
    }
}
