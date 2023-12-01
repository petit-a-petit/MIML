package com.petitapetit.miml.domain.playlist.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class PlayList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListId;

    private String name;

    private Long memberId;
    @OneToMany(mappedBy = "playList")
    private Set<TrackPlayList> trackPlayLists = new HashSet<>();

    private Boolean isPublic;

    public void updateIsPublic(Boolean isPublic){
        this.isPublic = isPublic;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
