package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import com.petitapetit.miml.domain.notification.TempUserRepository;
import com.petitapetit.miml.domain.notification.model.FriendRequestedNotification;
import com.petitapetit.miml.domain.notification.model.SharePlaylistRequestedNotification;
import com.petitapetit.miml.domain.notification.model.SongAddedNotification;
import com.petitapetit.miml.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TempUserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @EventListener
    @Transactional
    public void handleSongEvent(SongAddedEvent event) {
        TempSong song = event.getSong();

        Set<TempUser> users = userRepository.findByLikeArtistsSetContaining(song.getArtist());

        for (TempUser user : users) {
            SongAddedNotification notification = new SongAddedNotification(user, song);
            mailService.sendEmail(notification);
            notificationRepository.save(notification);
        }
    }

    @EventListener
    @Transactional
    public void handleFriendRequestEvent(FriendRequestedEvent event) {
        FriendRequestedNotification notification = new FriendRequestedNotification(event.getUser());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
    @EventListener
    @Transactional
    public void handleSharePlaylistRequestEvent(SharePlaylistRequestedEvent event) {
        SharePlaylistRequestedNotification notification = new SharePlaylistRequestedNotification(event.getTempUser());
        mailService.sendEmail(notification);
        notificationRepository.save(notification);
    }
}

