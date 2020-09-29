package com.carRental.service.emailService;

import com.carRental.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderServiceTestSuite {

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
        Mail mail = new Mail("email@test.com", "ccemail@test.com", "subject", "message");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setCc((mail.getToCc()));
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        emailSenderService.sendMail(mail);

        // Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}
