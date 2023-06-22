package rikkei.academy.service.sendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail,String subject,String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("minhkhiet1415@gmail.com");
        messageHelper.setSubject(subject);
        messageHelper.setTo(toEmail);
        boolean html = true;
        messageHelper.setText(body,html);
        javaMailSender.send(message);
    }
}
