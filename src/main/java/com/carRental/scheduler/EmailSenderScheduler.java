package com.carRental.scheduler;

import com.carRental.domain.Mail;
import com.carRental.scheduler.config.AdminConfig;
import com.carRental.service.EmailSenderService;
import com.carRental.strategy.EmailBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderScheduler {
    private static final String SUBJECT = "Car rental: Your daily email!";

    private final EmailSenderService emailSenderService;
    private final AdminConfig adminConfig;
    private final EmailBodyService emailBodyService;

    @Autowired
    public EmailSenderScheduler(EmailSenderService emailSenderService, AdminConfig adminConfig,
                                @Qualifier("statisticsEmailBodyService") EmailBodyService emailBodyService) {
        this.emailSenderService = emailSenderService;
        this.adminConfig = adminConfig;
        this.emailBodyService = emailBodyService;
    }

    //@Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "0 0 6 * * *")
    public void sendDailyEmail() {
        emailSenderService.sendMail(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                emailBodyService.emailBodyCreate()));
    }
}
