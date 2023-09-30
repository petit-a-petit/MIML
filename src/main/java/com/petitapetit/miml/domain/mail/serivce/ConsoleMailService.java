package com.petitapetit.miml.domain.mail.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// local 프로파일이면 메일을 로그로 출력합니다.
@Profile("local")
@Service
@Slf4j
public class ConsoleMailService implements MailService{

    @Override
    public void sendEmail(String to) {
        log.info("send email to : {}",to);
    }
}
