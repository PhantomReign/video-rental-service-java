package com.videorentalservice.services.common;

import com.videorentalservice.VRSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Rave on 21.03.2017.
 */

@Component
public class EmailServiceImplementation {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${support.email}") String supportEmail;

    public void sendEmail(String to, String subject, String content)
    {
        try
        {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(supportEmail);
            message.setTo(to);
            message.setText(content, true);

            javaMailSender.send(message.getMimeMessage());
        }
        catch (MailException | MessagingException e)
        {
            throw new VRSException("Unable to send email");
        }
    }

}
