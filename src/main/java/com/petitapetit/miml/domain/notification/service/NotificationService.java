package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.mail.serivce.MailService;
import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import com.petitapetit.miml.domain.notification.TempUserRepository;
import com.petitapetit.miml.domain.notification.model.Notification;
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
            Notification notification = new Notification(user, song);
            String userEmail = notification.getUserEmail();
            mailService.sendEmail(userEmail);
            notificationRepository.save(notification);
        }
    }
}

