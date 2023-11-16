package com.petitapetit.miml.domain.track;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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


    // getters, setters 생략
}

