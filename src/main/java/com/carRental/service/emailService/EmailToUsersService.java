package com.carRental.service.emailService;

import com.carRental.config.AdminConfiguration;
import com.carRental.domain.Car;
import com.carRental.domain.Mail;
import com.carRental.domain.Rental;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.domain.dto.UserDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailToUsersService {

    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;
    private final AdminConfiguration adminConfiguration;
    private static final String WELCOME_SUBJECT = "Welcome in our Car rental service!";
    private static final String CREATE_RENTAL = "New rental registered!";
    private static final String EXTEND_RENTAL = "Rental extended!";
    private static final String CLOSE_RENTAL = "Rental has been closed!";

    @Autowired
    public EmailToUsersService(EmailSenderService emailSenderService, UserRepository userRepository,
                               CarRepository carRepository, RentalRepository rentalRepository,
                               AdminConfiguration adminConfiguration) {
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
        this.adminConfiguration = adminConfiguration;
    }

    public void sendEmailAboutCreatingUser(UserDto userDto) {
        emailSenderService.sendMail(new Mail(
                userDto.getEmail(),
                WELCOME_SUBJECT,
                greetingsMessageCreate(userDto)));
    }

    public void sendEmailAboutCreatingRental(RentalDto rentalDto, String subjectType) throws CarNotFoundException,
            UserNotFoundException, RentalNotFoundException {
        Rental rental = rentalRepository.findById(rentalDto.getId()).orElseThrow(RentalNotFoundException::new);
        sendEmailAboutRental(rental, subjectType);
    }

    public void sendEmailAboutExtendingRental(RentalExtensionDto rentalExtensionDto, String subjectType) throws
            RentalNotFoundException, UserNotFoundException, CarNotFoundException {
        Rental rental = rentalRepository.findById(rentalExtensionDto.getRentalId()).orElseThrow(RentalNotFoundException::new);
        sendEmailAboutRental(rental, subjectType);
    }

    public void sendEmailAboutClosingRental(Long rentalId, String subjectType) throws RentalNotFoundException,
            UserNotFoundException, CarNotFoundException {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(RentalNotFoundException::new);
        sendEmailAboutRental(rental, subjectType);
    }

    private void sendEmailAboutRental(Rental rental, String subjectType) throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rental.getUser().getId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rental.getCar().getId()).orElseThrow(CarNotFoundException::new);

        emailSenderService.sendMail(new Mail(
                user.getEmail(),
                adminConfiguration.getAdminMail(),
                verifyEmailSubject(subjectType),
                rentalMessageCreate(rental, user, car, subjectType)));
    }

    private String verifyEmailSubject(String subjectType) {
        String subject = null;

        switch (subjectType) {
            case "created":
                subject = CREATE_RENTAL;
                break;
            case "extended":
                subject = EXTEND_RENTAL;
                break;
            case "closed":
                subject = CLOSE_RENTAL;
                break;
        }
        return subject;
    }

    private String rentalMessageCreate(Rental rental, User user, Car car, String messageType) {
        return ("\n Hello " + user.getName() + " !" +
                "\n Your rental has been " + messageType + ". Rental details: \n" +
                "\n\t Id: " + rental.getId() +
                "\n\t Rental starts: " + rental.getRentedFrom() +
                "\n\t Rental ends: " + rental.getRentedTo() +
                "\n\t Duration: " + rental.getDuration() +
                "\n\t Cost : " + rental.getCost() +
                "\n\t Car rented: " + car.getBrand() + " - " + car.getModel() + "\n" +
                "\n Have a nice day!");
    }

    private String greetingsMessageCreate(UserDto userDto) {
        return ("\n Hello " + userDto.getName() + " !" +
                "\n Your account has been created. We encourage You to check out cars on our website. \n" +
                "\n If You have any questions don't hesitate to contact us. \n" +
                "\n Have a nice day!");
    }
}
