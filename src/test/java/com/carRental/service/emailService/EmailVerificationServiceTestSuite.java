package com.carRental.service.emailService;

import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.extrernalApi.emailVerificationApi.client.EmailVerificationClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailVerificationServiceTestSuite {

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Mock
    private EmailVerificationClient emailVerificationClient;

    @Test
    public void verifyEmailTest() {
        //Given
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto("true", "true", "true");
        when(emailVerificationClient.verifyEmail("tamagotchi3377@gmail.com")).thenReturn(emailVerificationDto);

        //When
        EmailVerificationDto result = emailVerificationService.verifyEmail("tamagotchi3377@gmail.com");

        //Then
        assertEquals("true", result.getDnsCheck());
        assertEquals("true", result.getSmtpCheck());
        assertEquals("true", result.getFormatCheck());
    }
}
