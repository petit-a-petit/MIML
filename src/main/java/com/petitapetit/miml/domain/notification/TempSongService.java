package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.notification.event.SongAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TempSongService {
    private final TempSongRepository tempSongRepository;
    private final TempArtistRepository tempArtistRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void addNewSong(String artistName, String songName) {
        TempArtist artist = tempArtistRepository.findByName(artistName);
        TempSong newSong = new TempSong(songName, artist);
        tempSongRepository.save(newSong);

        // song 추가 이벤트 생성 및 발행
        SongAddedEvent songAddedEvent = new SongAddedEvent(this, newSong);
        applicationEventPublisher.publishEvent(songAddedEvent);
    }
}
