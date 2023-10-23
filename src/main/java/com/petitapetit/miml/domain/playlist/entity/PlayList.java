package com.petitapetit.miml.domain.playlist.entity;

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

    private Boolean isPublic;
}
