package com.ksmart47.cbtool.service;


import com.ksmart47.collaborationtool.dto.Member;
import com.ksmart47.collaborationtool.mapper.LoginMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@AllArgsConstructor
@Log4j2
@Transactional
@Service
public class LoginService {

    private final LoginMapper loginMapper;
    private final JavaMailSender emailSender;

    public String emailCheck(Member member){

        Member emailCheckResult = loginMapper.emailCheck(member);

        if(emailCheckResult == null){
            return "false";
        }

        return "true";
    }

    public String sendEmail(String to){

        String randomCode = generateRandomString(8);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("협업툴 인증 코드입니다.");
        message.setText("인증 코드 : " + randomCode);
        emailSender.send(message);

        return randomCode;
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }


}
