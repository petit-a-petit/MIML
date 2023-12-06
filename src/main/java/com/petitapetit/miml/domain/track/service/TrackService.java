package com.petitapetit.miml.domain.track.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.notification.event.TrackAddedEvent;
import com.petitapetit.miml.domain.track.repository.ArtistTrackRepository;
import com.petitapetit.miml.domain.track.repository.TrackRepository;
import com.petitapetit.miml.domain.track.dto.TrackDto;
import com.petitapetit.miml.domain.track.entity.ArtistTrack;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    private final TrackRepository trackRepository;
    private final ArtistTrackRepository artistTrackRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(propagation = Propagation.REQUIRED)
    public Track addNewSong(TrackDto trackDto, List<Artist> artists) {
        log.info("음악 생성 : {}", trackDto.getTrackName());
        log.info("현재 스레드 정보 : {}", Thread.currentThread());
        checkInputValue(trackDto);

        Track track = new Track(trackDto);
        Track savedTrack = trackRepository.save(track);
        for(Artist artist : artists) {
            ArtistTrack artistTrack = new ArtistTrack(artist, savedTrack);
            artistTrackRepository.save(artistTrack);
        }

        // song 추가 이벤트 생성 및 발행
        TrackAddedEvent trackAddedEvent = new TrackAddedEvent(savedTrack, artists);
        try {
            applicationEventPublisher.publishEvent(trackAddedEvent);
        } catch (Exception e) {
            System.out.println("메일 전송 실패");
        }

        return savedTrack;
    }

    private void checkInputValue(TrackDto trackDto) {
        if (trackDto.getArtistNames() == null) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public List<Artist> getArtistsByTrack(Track track) {
        List<ArtistTrack> artistTrack = artistTrackRepository.findAllByTrack(track);
        return artistTrack.stream().map(ArtistTrack::getArtist).collect(Collectors.toList());
    }
}