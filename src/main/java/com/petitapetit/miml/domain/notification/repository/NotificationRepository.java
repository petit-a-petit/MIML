package com.petitapetit.miml.domain.notification.repository;

import com.petitapetit.miml.domain.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Transactional
    void deleteByUserEmail(String userEmail);
    List<Notification> findAllByUserEmail(String userEmail);
}
