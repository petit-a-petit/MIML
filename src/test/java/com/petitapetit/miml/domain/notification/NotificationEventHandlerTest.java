package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import com.petitapetit.miml.domain.notification.entity.FriendRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.Notification;
import com.petitapetit.miml.domain.notification.entity.SharePlaylistRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.TrackAddedNotification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import com.petitapetit.miml.domain.notification.event.FriendRequestedEvent;
import com.petitapetit.miml.domain.notification.service.NotificationEventHandler;
import com.petitapetit.miml.domain.notification.event.SharePlaylistRequestedEvent;
import com.petitapetit.miml.domain.notification.event.TrackAddedEvent;
import com.petitapetit.miml.domain.track.Track;
import com.petitapetit.miml.domain.track.TrackDto;
import com.petitapetit.miml.test.ServiceTest;
import java.util.ArrayList;
import java.util.List;
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
    private MemberRepository userRepository;
    @Mock
    private MailService mailService;

    @Test
    @DisplayName("신곡이 추가되었을 때, 해당 노래의 아티스트를 좋아요 한 사용자에게 알림이 간다.")
    public void testHandleSongEvent() {
        // given
        TrackDto dto = new TrackDto(1,"spotify:url","artist","trackName","JYP","2","1","1","100");
        Track track = new Track(dto);
        TrackAddedEvent event = new TrackAddedEvent(track);
        Set<Member> users = new HashSet<>();
        users.add(new Member());

        when(userRepository.findByLikedArtistNames(any())).thenReturn(users);

        // when
        notificationEventHandler.handleSongEvent(event);

        // then
        verify(mailService, times(users.size())).sendEmail(any(TrackAddedNotification.class));
        verify(notificationRepository, times(users.size())).save(any(TrackAddedNotification.class));
    }

    @Test
    @DisplayName("신곡 추가 시 좋아요 한 사용자가 없으면 메일과 알림은 발송/저장 되지 않는다.")
    public void testHandleSongEvent_NoLikedUsers() {
        // given
        List<Artist> artist = new ArrayList<>(List.of(new Artist("artist")));
        Set<Member> noUsers = Collections.emptySet();

        TrackDto dto = new TrackDto(1,"spotify:url","artist","trackName","JYP","2","1","1","100");
        Track songByNoLikedArtists = new Track(dto);
        TrackAddedEvent event = new TrackAddedEvent(songByNoLikedArtists);

        when(userRepository.findByLikedArtistNames(any())).thenReturn(noUsers);

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
        FriendRequestedEvent event = new FriendRequestedEvent(user1, user2);

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
        SharePlaylistRequestedEvent event = new SharePlaylistRequestedEvent(user1, user2);

        // when
        notificationEventHandler.handleSharePlaylistRequestEvent(event);

        // then
        verify(mailService, times(1)).sendEmail(any(SharePlaylistRequestedNotification.class));
        verify(notificationRepository, times(1)).save(any(SharePlaylistRequestedNotification.class));
    }

}
