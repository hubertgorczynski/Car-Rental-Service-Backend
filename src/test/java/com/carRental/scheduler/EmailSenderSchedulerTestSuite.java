package com.carRental.scheduler;

import com.carRental.config.AdminConfiguration;
import com.carRental.service.emailService.EmailSenderService;
import com.carRental.strategy.EmailBodyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderSchedulerTestSuite {

    @InjectMocks
    private EmailSenderScheduler emailSenderScheduler;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private AdminConfiguration adminConfiguration;

    @Mock
    private EmailBodyService emailBodyService;

    @Test
    public void shouldSendEmail() {
        //Given
        when(adminConfiguration.getAdminMail()).thenReturn("tamagotchi3377@gmail.com");
        when(emailBodyService.emailBodyCreate()).thenReturn("message");
        doNothing().when(emailSenderService).sendMail(any());

        //When
        emailSenderScheduler.sendDailyEmail();

        //Then
        verify(emailSenderService, times(1)).sendMail(any());
    }
}
