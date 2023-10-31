package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import com.petitapetit.miml.domain.notification.TempUserRepository;
import com.petitapetit.miml.domain.notification.entity.FriendRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.SharePlaylistRequestedNotification;
import com.petitapetit.miml.domain.notification.entity.SongAddedNotification;
import com.petitapetit.miml.domain.notification.event.*;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
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
//@Async("asyncExecutor") // 비동기로 처리!
public class NotificationEventHandler {

    private final TempUserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @TransactionalEventListener(classes = SongAddedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleSongEvent(SongAddedEvent event) {
        log.info("이벤트 감지 : {}",event);
        log.info("현재 스레드 정보 : {}", Thread.currentThread());
        TempSong song = event.getSong();

        Set<TempUser> users = userRepository.findByLikeArtistsSetContaining(song.getArtist());

        for (TempUser user : users) {
            sendToUserAboutNewSongNotification(song, user);
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void sendToUserAboutNewSongNotification(TempSong song, TempUser user) {
        SongAddedNotification notification = new SongAddedNotification(user, song);
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }

    @TransactionalEventListener(classes = FriendRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFriendRequestEvent(FriendRequestedEvent event) {
        log.debug("이벤트 감지 : {}",event);
        log.debug("현재 스레드 정보 : {}", Thread.currentThread());

        FriendRequestedNotification notification = new FriendRequestedNotification(event.getCurrentUserName(), event.getRequestedUserName());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
    @TransactionalEventListener(classes = SharePlaylistRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleSharePlaylistRequestEvent(SharePlaylistRequestedEvent event) {
        log.debug("이벤트 감지 : {}",event);
        log.debug("현재 스레드 정보 : {}", Thread.currentThread());

        SharePlaylistRequestedNotification notification = new SharePlaylistRequestedNotification(event.getCurrentUserEmail(), event.getRequestedUserEmail());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
}

