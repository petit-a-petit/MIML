package com.petitapetit.miml.global.batch;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.track.ArtistTrack;
import com.petitapetit.miml.domain.track.ArtistTrackRepository;
import com.petitapetit.miml.domain.track.Track;
import com.petitapetit.miml.domain.track.TrackDto;
import com.petitapetit.miml.domain.track.TrackService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TrackItemProcessor implements ItemProcessor<TrackDto, Track> {
    private final ArtistRepository artistRepository;

    private final ArtistTrackRepository artistTrackRepository;
    private final TrackService trackService;
    @Override
    public Track process(final TrackDto trackDto) {
        List<ArtistTrack> artistTracks = Arrays.stream(trackDto.getArtistNames().split(","))
                .map(String::trim)
                .map(name -> artistRepository.findByName(name).orElseGet(() -> artistRepository.save(new Artist(name))))
                .map(artist -> {
                    ArtistTrack artistTrack = new ArtistTrack(artist);
                    return artistTrackRepository.save(artistTrack);
                })
                .collect(Collectors.toList());

        Track newTrack = trackService.addNewSong(trackDto, artistTracks);
        return newTrack;
    }
}

