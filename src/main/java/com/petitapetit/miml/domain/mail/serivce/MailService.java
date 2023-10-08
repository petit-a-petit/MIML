package com.petitapetit.miml.domain.mail.serivce;

import com.petitapetit.miml.domain.notification.entity.Notification;

// 메일 서비스를 실행 환경에 따라 추상화합니다.
public interface MailService {
    /**
     * 신곡 알림 이메일을 지정된 수신자에게 발송합니다.
     *
     * @param notification 사용자에게 보낼 알림
     */
    void sendEmail(Notification notification);
}
