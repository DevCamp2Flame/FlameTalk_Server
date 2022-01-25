package com.devcamp.flametalk.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 이메일 인증을 위한 클래스입니다.
 */
@RequiredArgsConstructor
@Service
public class GmailSenderService {

  private final JavaMailSender javaMailSender;

  /**
   * 메일 전송 메소드입니다.
   *
   * @param nickname 별명
   * @param email    이메일
   */
  public void sendEmailMessage(String nickname, String email) {

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    // todo : 인증 코드 or url 전송 방식 중 선택

    simpleMailMessage.setTo(email);
    simpleMailMessage.setSubject(nickname + "님, 메일 인증을 완료하세요.");
    simpleMailMessage.setText("메일 인증을 완료해야 서비스를 이용하실 수 있습니다.");

    javaMailSender.send(simpleMailMessage);
  }
}