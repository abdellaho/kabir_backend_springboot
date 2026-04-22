package com.kabir.kabirbackend.config.jobs;

import com.kabir.kabirbackend.config.security.encryption.Encryption;
import com.kabir.kabirbackend.entities.Etablissement;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Properties;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendEmail(Etablissement etablissement, String from, String to, String subject, String body, boolean isHtml, File file) {
        JavaMailSender mailSender = createMailSender(etablissement);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);
            helper.setFrom(from);

            if (file != null && file.exists()) {
                helper.addAttachment(file.getName(), file);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Failed to send email", e);
        }
    }

    public JavaMailSender createMailSender(Etablissement etablissement) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(etablissement.getHostMail());
        mailSender.setPort(etablissement.getPort());
        mailSender.setUsername(etablissement.getUserMail());
        mailSender.setPassword(Encryption.strDecrypt(etablissement.getPaswordMail(), 7));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}