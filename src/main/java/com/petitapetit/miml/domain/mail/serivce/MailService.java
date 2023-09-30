package com.petitapetit.miml.domain.mail.serivce;

// 메일 서비스를 실행 환경에 따라 추상화합니다.
public interface MailService {
    /**
     * 신곡 알림 이메일을 지정된 수신자에게 발송합니다.
     *
     * @param to 이메일을 받을 사용자의 이메일 주소
     */
    void sendEmail(String to);
}
