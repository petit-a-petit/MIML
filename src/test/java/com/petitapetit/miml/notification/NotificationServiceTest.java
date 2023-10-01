package com.petitapetit.miml.notification;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.TempArtist;
import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import com.petitapetit.miml.domain.notification.TempUserRepository;
import com.petitapetit.miml.domain.notification.model.Notification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import com.petitapetit.miml.domain.notification.service.NotificationService;
import com.petitapetit.miml.domain.notification.service.SongAddedEvent;
import com.petitapetit.miml.test.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.*;

public class NotificationServiceTest extends ServiceTest {

    private NotificationService notificationService;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private TempUserRepository userRepository;
    @Mock
    private MailService mailService;

    @BeforeEach
    public void setUp() {
        // notificationService의 실제 서비스 로직을 테스트 하기 위함입니다.
        notificationService = new NotificationService(userRepository, notificationRepository, mailService);
    }

    @Test
    @DisplayName("신곡이 추가되었을 때, 해당 노래의 아티스트를 좋아요 한 사용자에게 알림이 간다.")
    public void testHandleSongEvent() {
        // given
        TempArtist artist1 = new TempArtist("artist1");
        TempUser user1 = new TempUser("user1@example.com");
        TempUser user2 = new TempUser("user2@example.com");
        user1.likeArtist(artist1);

        Set<TempUser> usersWhoLikeArtist1 = Collections.singleton(user1);

        when(userRepository.findByLikeArtistsSetContaining(artist1)).thenReturn(usersWhoLikeArtist1);

        // songService 내부에서 실행 되어야 하는 로직입니다.
        // 실제 songService에서는 song 추가 시 NotificationService에 의존도를 낮추기 위해 ApplicationEventPublisher를 사용하여 이벤트를 발행합니다.
        // 하지만 현재 테스트는 NotificationService의 테스트 코드로, songService의 이벤트 발행은 확인하지 않습니다.
        TempSong songByArtist1 = new TempSong("song", artist1);
        SongAddedEvent event = new SongAddedEvent(this, songByArtist1);

        // when
        notificationService.handleSongEvent(event);

        // then
        verify(mailService, times(1)).sendEmail(user1.getEmail());
        verify(mailService, times(0)).sendEmail(user2.getEmail());
    }

    @Test
    @DisplayName("신곡 추가 시 좋아요 한 사용자가 없으면 메일과 알림은 발송/저장 되지 않는다.")
    public void testHandleSongEvent_NoLikedUsers() {
        // given
        TempArtist artist = new TempArtist("artist");
        Set<TempUser> noUsers = Collections.emptySet();

        when(userRepository.findByLikeArtistsSetContaining(artist)).thenReturn(noUsers);

        TempSong songByNoLikedArtists = new TempSong("song", artist);
        SongAddedEvent event = new SongAddedEvent(this, songByNoLikedArtists);

        // when
        notificationService.handleSongEvent(event);

        // then
        verify(mailService, never()).sendEmail(anyString());
        verify(notificationRepository, never()).save(any(Notification.class));
    }
}
