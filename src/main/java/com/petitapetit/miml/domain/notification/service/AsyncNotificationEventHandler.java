package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.notification.event.TrackAddedEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncNotificationEventHandler {
    private final NotificationEventHandler notificationEventHandler;

    @TransactionalEventListener(classes = TrackAddedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleSongEvent(TrackAddedEvent event) {
        CompletableFuture<Void> result = notificationEventHandler.handleSongEvent(event);
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
