package com.petitapetit.miml.domain.track;

import com.petitapetit.miml.domain.notification.event.TrackAddedEvent;
import java.util.List;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(propagation = Propagation.REQUIRED)
    public Track addNewSong(TrackDto trackDto, List<ArtistTrack> artistTracks) {
        log.info("음악 생성 : {}", trackDto.getTrackName());
        log.info("현재 스레드 정보 : {}", Thread.currentThread());
        checkInputValue(trackDto);

        Track track = new Track(trackDto);
        track.setArtistTracks(artistTracks);
        Track savedTrack = trackRepository.save(track);

        // song 추가 이벤트 생성 및 발행
        TrackAddedEvent trackAddedEvent = new TrackAddedEvent(track);
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
}
