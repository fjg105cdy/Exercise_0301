package com.yian.banking_service_exercise_01.services.impl;

import com.yian.banking_service_exercise_01.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.concurrent.Executor;

import java.util.concurrent.Executor;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final Executor executor;

    public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine, @Qualifier ("emailExecutor") Executor executor
    ) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.executor = executor;
    }

    @Override
    public void sendEmail(String to, String name, String subject) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText("welcome to banking application");
//        mailSender.send(message);
        executor.execute(() -> {
            try{
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                Context context = new Context();
                context.setVariable("name", name);
                String htmlContent= templateEngine.process("welcome-email", context);

                helper.setTo(to);
                helper.setSubject("Welcome to Banking application exercise 01");
                helper.setText(htmlContent,true);

                mailSender.send(mimeMessage);}
            catch(Exception e){
                System.err.println("Email failed to send"+e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendEmailVerificationCode(String to, String code) {
        executor.execute(() -> {
            try{
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                Context context = new Context();
                context.setVariable("code", code);
                String htmlContent = templateEngine.process("verification-email",context);

                helper.setTo(to);
                helper.setSubject("이메일 인증 코드 전송");
                helper.setText(htmlContent,true);

                mailSender.send(mimeMessage);

            } catch(Exception ex){
                System.err.println("Email failed to send"+ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
