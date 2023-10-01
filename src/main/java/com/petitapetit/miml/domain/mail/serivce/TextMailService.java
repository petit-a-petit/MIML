package com.petitapetit.miml.domain.mail.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// test가 아니면 실제 메일 전송
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
@Service
public class TextMailService implements MailService{

    private final JavaMailSender javaMailSender;

    /**
     * 신곡 알림 이메일을 지정된 수신자에게 발송합니다.
     *
     * @param to 이메일을 받을 사용자의 이메일 주소
     *
     * <p>이 메서드는 SMTP를 통해 신곡 알림 이메일을 발송합니다.
     * 이메일 제목은 "스트리밍 서비스 신곡 알림"으로 설정되며,
     * 본문 내용은 "좋아요 한 가수의 신곡이 발매되었습니다!"로 설정됩니다.</p>
     *
     * <p>이 메서드는 {@link MessagingException} 예외를 처리하며,
     * 예외가 발생하면 로그에 오류 메시지를 기록합니다.</p>
     */
    @Override
    public void sendEmail(String to) {
        log.info("start to send mail : {}",to);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try{
            mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("스트리밍 서비스 신곡 알림");
            mimeMessageHelper.setText("좋아요 한 가수의 신곡이 발매되었습니다!");
            javaMailSender.send(message);
            log.info("sent mail to : {}", to);
        } catch (MessagingException e) {
            log.error("failed to send mail", e);
        }
    }
}
