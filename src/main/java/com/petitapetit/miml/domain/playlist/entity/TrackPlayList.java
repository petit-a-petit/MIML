package com.petitapetit.miml.domain.playlist.entity;

import com.petitapetit.miml.domain.track.entity.Track;
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
public class TrackPlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_list_id")
    private PlayList playList;

    public TrackPlayList(Track track, PlayList playList) {
        this.track = track;
        this.playList = playList;
    }
}
