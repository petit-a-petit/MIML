package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;
import com.petitapetit.miml.domain.notification.entity.FriendRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.SharePlaylistRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.TrackAddedNotification;
import com.petitapetit.miml.domain.notification.event.*;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventHandler {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @Async
    public CompletableFuture<Void> handleSongEvent(TrackAddedEvent event) {
        Track track = event.getTrack();

        List<Artist> artists = track.getArtists();
        Set<Member> users = memberRepository.findByLikedArtistNames(
                artists.stream().map(artist -> artist.getName()).collect(
                        Collectors.toList()));

        for (Member user : users) {
            sendToUserAboutNewSongNotification(track, user);
        }
        return null;
    }

    @Transactional(propagation = Propagation.NESTED)
    public void sendToUserAboutNewSongNotification(Track song, Member user) {
        TrackAddedNotification notification = TrackAddedNotification.from(song, user);
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }

    @TransactionalEventListener(classes = FriendRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleFriendRequestEvent(FriendRequestedEvent event) {
        FriendRequestedNotification notification = FriendRequestedNotification.of(event);
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }

    @TransactionalEventListener(classes = SharePlaylistRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleSharePlaylistRequestEvent(SharePlaylistRequestedEvent event) {
        SharePlaylistRequestedNotification notification = SharePlaylistRequestedNotification.from(event);
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
}

