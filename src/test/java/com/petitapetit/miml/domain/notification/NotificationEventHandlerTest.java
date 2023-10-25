package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.entity.FriendRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.Notification;
import com.petitapetit.miml.domain.notification.entity.SharePlaylistRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.SongAddedNotification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import com.petitapetit.miml.domain.notification.event.FriendRequestedEvent;
import com.petitapetit.miml.domain.notification.service.NotificationEventHandler;
import com.petitapetit.miml.domain.notification.event.SharePlaylistRequestedEvent;
import com.petitapetit.miml.domain.notification.event.SongAddedEvent;
import com.petitapetit.miml.test.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class NotificationEventHandlerTest extends ServiceTest {

    @InjectMocks
    private NotificationEventHandler notificationEventHandler;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private TempUserRepository userRepository;
    @Mock
    private MailService mailService;

    @Test
    @DisplayName("신곡이 추가되었을 때, 해당 노래의 아티스트를 좋아요 한 사용자에게 알림이 간다.")
    public void testHandleSongEvent() {
        // given
        TempArtist artist = new TempArtist("artist");
        TempSong song = new TempSong("newSong",artist);
        SongAddedEvent event = new SongAddedEvent(this,song);
        Set<TempUser> users = new HashSet<>();
        users.add(new TempUser());

        when(userRepository.findByLikeArtistsSetContaining(any())).thenReturn(users);

        // when
        notificationEventHandler.handleSongEvent(event);

        // then
        verify(mailService, times(users.size())).sendEmail(any(SongAddedNotification.class));
        verify(notificationRepository, times(users.size())).save(any(SongAddedNotification.class));
    }

    @Test
    @DisplayName("신곡 추가 시 좋아요 한 사용자가 없으면 메일과 알림은 발송/저장 되지 않는다.")
    public void testHandleSongEvent_NoLikedUsers() {
        // given
        TempArtist artist = new TempArtist("artist");
        Set<TempUser> noUsers = Collections.emptySet();

        TempSong songByNoLikedArtists = new TempSong("song", artist);
        SongAddedEvent event = new SongAddedEvent(this, songByNoLikedArtists);

        when(userRepository.findByLikeArtistsSetContaining(artist)).thenReturn(noUsers);

        // when
        notificationEventHandler.handleSongEvent(event);

        // then
        verify(mailService, never()).sendEmail(any());
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    @DisplayName("친구 추가 이벤트 발생 시 mail이 보내지고 알림 내용이 저장된다.")
    void testHandleFriendRequestEvent() {
        // given
        TempUser user1 = new TempUser();
        TempUser user2 = new TempUser();
        FriendRequestedEvent event = new FriendRequestedEvent(this, user1, user2);

        // when
        notificationEventHandler.handleFriendRequestEvent(event);

        // then
        verify(mailService, times(1)).sendEmail(any(FriendRequestedNotification.class));
        verify(notificationRepository, times(1)).save(any(FriendRequestedNotification.class));
    }

    @Test
    @DisplayName("플레이리스트 공유 이벤트 발생 시 mail이 보내지고 알림 내용이 저장된다.")
    void testHandleSharePlaylistRequestEvent() {
        // given
        TempUser user1 = new TempUser();
        TempUser user2 = new TempUser();
        SharePlaylistRequestedEvent event = new SharePlaylistRequestedEvent(this, user1, user2);

        // when
        notificationEventHandler.handleSharePlaylistRequestEvent(event);

        // then
        verify(mailService, times(1)).sendEmail(any(SharePlaylistRequestedNotification.class));
        verify(notificationRepository, times(1)).save(any(SharePlaylistRequestedNotification.class));
    }

}
