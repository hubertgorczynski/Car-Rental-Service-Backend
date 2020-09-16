package com.carRental.scheduler;

import com.carRental.domain.Mail;
import com.carRental.domain.Status;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import com.carRental.scheduler.config.AdminConfig;
import com.carRental.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private static final String SUBJECT = "Car rental: Your daily email!";

    private final EmailSenderService emailSenderService;
    private final AdminConfig adminConfig;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public EmailScheduler(EmailSenderService emailSenderService, AdminConfig adminConfig, CarRepository carRepository,
                          UserRepository userRepository, RentalRepository rentalRepository) {
        this.emailSenderService = emailSenderService;
        this.adminConfig = adminConfig;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "0 0 6 * * *")
    public void sendDailyEmail() {
        long userRepositorySize = userRepository.count();
        long carRentedSize = carRepository.countAllByStatus(Status.RENTED);
        long carAvailableSize = carRepository.countAllByStatus(Status.AVAILABLE);
        long rentalRepositorySize = rentalRepository.count();

        emailSenderService.sendMail(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "\n Dear car rental administrator." +
                        "\n\t Below You can find daily statistics considering Your database: " +
                        "\n Current number of registered users: " + userRepositorySize +
                        "\n Current number of rented cars: " + carRentedSize +
                        "\n Current number of available cars: " + carAvailableSize +
                        "\n Current number of available cars: " + rentalRepositorySize +
                        "\n\t Have a nice day!"
        ));
    }
}
