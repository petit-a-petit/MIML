package com.petitapetit.miml.domain.notification.repository;

import com.petitapetit.miml.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
