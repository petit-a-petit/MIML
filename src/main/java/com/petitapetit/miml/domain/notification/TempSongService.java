package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.notification.event.SongAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TempSongService {
    private final TempSongRepository tempSongRepository;
    private final TempArtistRepository tempArtistRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewSong(String artistName, String songName) {
        log.info("음악 생성 : {}", songName);
        log.info("현재 스레드 정보 : {}", Thread.currentThread());
        checkInputValue(artistName, songName);

        TempArtist artist = tempArtistRepository.findByName(artistName);
        TempSong newSong = new TempSong(songName, artist);
        tempSongRepository.save(newSong);

        // song 추가 이벤트 생성 및 발행
        SongAddedEvent songAddedEvent = new SongAddedEvent(this, newSong);
        try {
            applicationEventPublisher.publishEvent(songAddedEvent);
        } catch (Exception e) {
            System.out.println("메일 전송 실패");
        }
    }

    private void checkInputValue(String artistName, String songName) {
        if(artistName == null || songName == null) {
            throw new IllegalArgumentException();
        }
    }
}
