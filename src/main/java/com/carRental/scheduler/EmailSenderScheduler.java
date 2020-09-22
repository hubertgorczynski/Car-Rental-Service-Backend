package com.carRental.scheduler;

import com.carRental.domain.Mail;
import com.carRental.config.AdminConfiguration;
import com.carRental.service.emailService.EmailSenderService;
import com.carRental.strategy.EmailBodyService;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailSenderScheduler {
    private static final String SUBJECT = "Car rental: Your daily email!";

    private final EmailSenderService emailSenderService;
    private final AdminConfiguration adminConfiguration;
    private final EmailBodyService emailBodyService;

    public EmailSenderScheduler(EmailSenderService emailSenderService, AdminConfiguration adminConfiguration,
                                EmailBodyService emailBodyService) {
        this.emailSenderService = emailSenderService;
        this.adminConfiguration = adminConfiguration;
        this.emailBodyService = emailBodyService;
    }

    //@Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "0 0 6 * * *")
    public void sendDailyEmail() {
        emailSenderService.sendMail(new Mail(
                adminConfiguration.getAdminMail(),
                SUBJECT,
                emailBodyService.emailBodyCreate()));
    }
}
