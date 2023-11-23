package com.petitapetit.miml.domain.track.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {
    private int rank;
    private String uri;
    private String artistNames;
    private String trackName;
    private String source;
    private String peakRank;
    private String previousRank;
    private String weeksOnChart;
    private String streams;
}

