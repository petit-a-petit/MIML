package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.entity.Notification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@Import({AsyncTestConfig.class})
public class NotificationTransactionIntegrationTest {
    @Autowired
    private TempSongRepository tempSongRepository;
    @Autowired
    private TempSongService tempSongService;
    @Autowired
    private TempUserRepository tempUserRepository;
    @Autowired
    private TempArtistRepository tempArtistRepository;
    @MockBean
    private MailService mailService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("음악 생성이 실패 시 이벤트가 발행되지 않아야 한다.")
    public void testNoEventOnSongCreationFailure() {
        // given: 유저 생성, 아티스트 이름과 노래 이름 설정
        String artistName = "Artist1";
        String songName = null;

        // when: addNewSong 메소드 호출하지만 내부 예외 발생
        assertThrows(IllegalArgumentException.class,
                () -> tempSongService.addNewSong(artistName, songName));

        // then: 저장된 곡과 알림이 없음을 확인
        List<TempSong> songs = tempSongRepository.findAll();
        List<Notification> notifications = notificationRepository.findAll();
        assertTrue(songs.isEmpty());
        assertTrue(notifications.isEmpty());
    }

    @Test
    @DisplayName("알림 생성이 실패해도 음악 생성은 성공한다.")
    public void testNotificationFailure() {
        // given: 아티스트와 노래, 그 아티스트를 좋아하는 유저 생성
        String artistName = "Artist1";
        String songName = "Song1";
        TempArtist artist = new TempArtist(artistName);
        tempArtistRepository.save(artist);

        TempUser tempUser = new TempUser("test123@test.com");
        tempUser.likeArtist(artist);
        tempUserRepository.save(tempUser);

        doThrow(new RuntimeException()).when(mailService).sendEmail(any(Notification.class));

        // when: 신곡 추가 이벤트가 발생하고 음악이 저장되었음을 확인
        tempSongService.addNewSong(artistName, songName);

        // then : 음악은 저장되고 알림은 저장
        List<TempSong> songs = tempSongRepository.findByArtistName(artistName);
        List<Notification> notifications = notificationRepository.findAll();
        assertTrue(
                songs.stream().anyMatch(song -> song.getName().equals(songName)));
        assertTrue(notifications.isEmpty());
    }

    @Test
    @DisplayName("음악 생성이 성공 시 알림이 저장되어야 한다.")
    public void testEventOnSongCreationSuccess() {
        // given: 유저 생성, 아티스트 이름과 노래 이름 설정
        String artistName = "Artist1";
        String songName = "Song1";
        TempArtist artist = new TempArtist(artistName);
        artist = tempArtistRepository.save(artist);

        TempUser tempUser = new TempUser("test123@test.com");
        tempUser.likeArtist(artist);
        tempUserRepository.save(tempUser);

        // when: addNewSong 메소드 호출
        tempSongService.addNewSong(artistName, songName);

        // then: 신곡 추가 이벤트가 발생했음을 확인
        List<TempSong> songs = tempSongRepository.findAll();
        List<Notification> notifications = notificationRepository.findAll();
        assertFalse(songs.isEmpty());
        assertFalse(notifications.isEmpty());
    }
}