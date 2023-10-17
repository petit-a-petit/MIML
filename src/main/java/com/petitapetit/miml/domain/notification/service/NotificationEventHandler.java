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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final TempUserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @TransactionalEventListener(classes = SongAddedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handleSongEvent(SongAddedEvent event) {
        TempSong song = event.getSong();

        Set<TempUser> users = userRepository.findByLikeArtistsSetContaining(song.getArtist());

        for (TempUser user : users) {
            SongAddedNotification notification = new SongAddedNotification(user, song);
            mailService.sendEmail(notification);
            notificationRepository.save(notification);
        }
    }

    @TransactionalEventListener(classes = FriendRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handleFriendRequestEvent(FriendRequestedEvent event) {
        FriendRequestedNotification notification = new FriendRequestedNotification(event.getCurrentUserName(), event.getRequestedUserName());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
    @TransactionalEventListener(classes = SharePlaylistRequestedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handleSharePlaylistRequestEvent(SharePlaylistRequestedEvent event) {
        SharePlaylistRequestedNotification notification = new SharePlaylistRequestedNotification(event.getCurrentUserEmail(), event.getRequestedUserEmail());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
}

