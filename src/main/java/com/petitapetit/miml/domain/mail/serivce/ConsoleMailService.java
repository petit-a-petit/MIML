package com.petitapetit.miml.domain.mail.serivce;

import com.petitapetit.miml.domain.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// test 프로파일이면 메일을 로그로 출력합니다.
@Profile("test")
@Service
@Slf4j
public class ConsoleMailService implements MailService{

    @Override
    public void sendEmail(Notification notification) {
        log.info("send email to : {}",notification.getUserEmail());
    }
}
