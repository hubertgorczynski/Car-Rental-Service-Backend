package com.carRental.config;

import com.carRental.scheduler.EmailSenderScheduler;
import com.carRental.service.emailService.EmailSenderService;
import com.carRental.strategy.EmailBodyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    public EmailSenderScheduler statisticsEmailScheduler(EmailSenderService emailSenderService,
                                                         AdminConfiguration adminConfiguration,
                                                         @Qualifier("statisticsEmailBodyService") EmailBodyService emailBodyService) {
        return new EmailSenderScheduler(emailSenderService, adminConfiguration, emailBodyService);
    }

    @Bean
    public EmailSenderScheduler reminderEmailScheduler(EmailSenderService emailSenderService,
                                                       AdminConfiguration adminConfiguration,
                                                       @Qualifier("reminderEmailBodyService") EmailBodyService emailBodyService) {
        return new EmailSenderScheduler(emailSenderService, adminConfiguration, emailBodyService);
    }
}
