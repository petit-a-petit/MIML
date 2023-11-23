package com.petitapetit.miml.domain.notification;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.artist.domain.ArtistRepository;
import com.petitapetit.miml.domain.artist.service.ArtistService;
import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.model.RoleType;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import com.petitapetit.miml.domain.member.service.MemberService;
import com.petitapetit.miml.domain.notification.entity.Notification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import com.petitapetit.miml.domain.track.entity.ArtistTrack;
import com.petitapetit.miml.domain.track.repository.ArtistTrackRepository;
import com.petitapetit.miml.domain.track.entity.Track;
import com.petitapetit.miml.domain.track.dto.TrackDto;
import com.petitapetit.miml.domain.track.repository.TrackRepository;
import com.petitapetit.miml.domain.track.service.TrackService;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class NotificationTransactionIntegrationTest {
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TrackService trackService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistTrackRepository artistTrackRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ArtistService artistService;
    @MockBean
    private MailService mailService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Test
    public void testFindByLikedArtists() {
        // 멤버 생성
        Member member = Member.builder()
                .name("Test User")
                .email("test@example.com")
                .role(RoleType.ROLE_USER)
                .provider(OAuth2Provider.SPOTIFY)
                .providerId("test")
                .build();
        memberRepository.save(member);

        String artistName = "Artist1";
        Artist artist = new Artist(artistName);
        artist = artistRepository.save(artist);

        TrackDto dto = new TrackDto(1,"spotify:url",artistName,"song","JYP","2","1","1","100");
        Track track = new Track(dto);
        track = trackRepository.save(track);

        ArtistTrack artistTrack = new ArtistTrack(artist);
        artistTrack.setTrack(track);
        artistTrackRepository.save(artistTrack);

        memberService.likeArtist(member,artist.getId());

        // 트랙의 아티스트를 좋아하는 멤버 찾기
        List<Artist> trackArtists = trackService.getArtistsByTrack(track);
        Set<Member> likedMembers = artistService.findMembersByLikedArtistNames(trackArtists);

        // 검증
        boolean containsMember = likedMembers.stream()
                .anyMatch(m -> m.getMemberId().equals(member.getMemberId()));
        assertTrue(containsMember);
    }
    @Nested
    class TrackAddedEventHandlerTest {
        @Test
        @DisplayName("음악 생성이 실패 시 이벤트가 발행되지 않아야 한다.")
        void testNoEventOnSongCreationFailure() {
            // given: 유저 생성, 아티스트 이름과 노래 이름 설정
            TrackDto dto = new TrackDto(1,"spotify:url",null,"trackName","JYP","2","1","1","100");

            // when: addNewSong 메소드 호출하지만 내부 예외 발생
            assertThrows(IllegalArgumentException.class,
                    () -> trackService.addNewSong(dto,null));

            // then: 저장된 곡과 알림이 없음을 확인
            List<Track> songs = trackRepository.findByName("trackName");
            List<Notification> notifications = notificationRepository.findAll();
            assertTrue(songs.isEmpty());
            assertTrue(notifications.isEmpty());
        }

        @Test
        @DisplayName("알림 생성이 실패해도 음악 생성은 성공한다.")
        void testNotificationFailure() {
            // given: 아티스트와 노래, 그 아티스트를 좋아하는 유저 생성
            String artistName = "Artist1";
            Artist artist = new Artist(artistName);
            artist = artistRepository.save(artist);

            TrackDto dto = new TrackDto(1,"spotify:url",artistName,"song","JYP","2","1","1","100");
            Track track = new Track(dto);
            track = trackRepository.save(track);

            ArtistTrack artistTrack = new ArtistTrack(artist);
            artistTrack.setTrack(track);
            artistTrack = artistTrackRepository.save(artistTrack);

            Member member = Member.builder()
                    .name("Test User")
                    .email("test@example.com")
                    .role(RoleType.ROLE_USER)
                    .provider(OAuth2Provider.SPOTIFY)
                    .providerId("test")
                    .build();
            member = memberRepository.save(member);
            memberService.likeArtist(member,artist.getId());

            doThrow(new RuntimeException()).when(mailService).sendEmail(any(Notification.class));

            // when: 신곡 추가 이벤트가 발생하고 음악이 저장되었음을 확인
            trackService.addNewSong(dto, List.of(artistTrack));

            // then : 음악은 저장되고 알림은 저장
            List<Track> songs = trackRepository.findByName("song");
            List<Notification> notifications = notificationRepository.findAll();
            assertTrue(songs.stream().anyMatch(song -> song.getName().equals("song")));
            assertTrue(notifications.isEmpty());
        }

        @Test
        @DisplayName("음악 생성이 성공 시 알림이 저장되어야 한다.")
        void testEventOnSongCreationSuccess() {
            // given: 유저 생성, 아티스트 이름과 노래 이름 설정
            String artistName = "Artist1";
            Artist artist = new Artist(artistName);
            artist = artistRepository.save(artist);

            TrackDto dto = new TrackDto(1,"spotify:url",artistName,"song","JYP","2","1","1","100");
            Track track = new Track(dto);
            track = trackRepository.save(track);

            ArtistTrack artistTrack = new ArtistTrack(artist);
            artistTrack.setTrack(track);
            artistTrack = artistTrackRepository.save(artistTrack);

            Member member = Member.builder()
                    .name("Test User")
                    .email("test@example.com")
                    .role(RoleType.ROLE_USER)
                    .provider(OAuth2Provider.SPOTIFY)
                    .providerId("test")
                    .build();
            member = memberRepository.save(member);
            memberService.likeArtist(member,artist.getId());

            // when: addNewSong 메소드 호출
            trackService.addNewSong(dto, List.of(artistTrack));

            // then: 신곡 추가 이벤트가 발생했음을 확인
            List<Track> songs = trackRepository.findAll();
            List<Notification> notifications = notificationRepository.findAll();
            assertFalse(songs.isEmpty());
            assertFalse(notifications.isEmpty());
        }
    }
}