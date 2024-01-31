package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@DependsOn(value = "restaurantServiceImpl")
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String FROM = "spoon.find.your.restaurant@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private URL appBaseUrl;

    @Autowired
    private MessageSource messageSource;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private void sendMail(String to, String subject, String template, Map<String, Object> variables, Locale locale) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(getHtmlBody(template, variables, locale), true);

            System.out.printf("Locale: %s", locale.toString());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.error("Sending email failed");
        }
    }

    private String getHtmlBody(String template, Map<String, Object> variables, Locale locale) {
        Context thymeleafContext = new Context(locale);
        if (variables == null) {
            variables = new HashMap<>();
        }
        thymeleafContext.setVariables(variables);
        return templateEngine.process(template, thymeleafContext);
    }

    @Async
    @Override
    public void sendUserVerificationEmail(String email, String token, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        final String url = appBaseUrl.toString() + "verify?token=" + token;
        mailMap.put("url", url);
        final String subject = messageSource.getMessage("email.verify_account.subject", null, locale);
        LOGGER.debug("Sending to {} verification token link", email);
        sendMail(email, subject, "verify-account.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendConfirmationEmail(String email, String name, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        final String url = appBaseUrl.toString() + "login";
        mailMap.put("url", url);
        mailMap.put("email", email);
        mailMap.put("name", name);
        final String subject = messageSource.getMessage("email.confirm_account.subject", null, locale);
        LOGGER.debug("Sending confirmation email to {}", email);
        sendMail(email, subject, "confirm-account.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendReviewNotification(Review review, Locale locale) {
        Restaurant restaurant = review.getRest();
        User user = review.getReviewer();

        final String url = appBaseUrl.toString() + "restaurants/" + restaurant.getId();

        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("user", user.getUsername());
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("review", review.getText());
        mailMap.put("rating", String.valueOf(review.getRating()));

        final String subject = messageSource.getMessage("email.review_notification.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} review notification", restaurant.getEmail());
        sendMail(restaurant.getEmail(), subject, "review-notification.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendResponseNotification(Review review, String answer, Locale locale) {
        Restaurant restaurant = review.getRest();
        User user = review.getReviewer();

        final String url = appBaseUrl.toString() + "/restaurants/" + restaurant.getId();

        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("review", review.getText());
        mailMap.put("rating", String.valueOf(review.getRating()));
        mailMap.put("answer", answer);

        final String subject = messageSource.getMessage("email.response_notification.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} response notification", user.getEmail());
        sendMail(user.getEmail(), subject, "response-notification.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendBookingInfo(Booking booking, Locale locale) {
        Restaurant restaurant = booking.getRest();
        User user = booking.getBooker();

        final String url = appBaseUrl.toString();
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("date", formatter.format(booking.getDate().toString()));
        mailMap.put("hour", booking.getTime());
        mailMap.put("diners", String.valueOf(booking.getDiners()));
        mailMap.put("restaurant", restaurant.getFullName());

        final String subject = messageSource.getMessage("email.booking_info.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} booking information", user.getEmail());
        sendMail(user.getEmail(), subject, "booking-info.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendBookingNotification(Booking booking, Locale locale) {
        Restaurant restaurant = booking.getRest();
        User user = booking.getBooker();

        final String url = appBaseUrl.toString();
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("date", formatter.format(booking.getDate().toString()));
        mailMap.put("hour", booking.getTime());
        mailMap.put("diners", String.valueOf(booking.getDiners()));
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("user", user.getUsername());
        mailMap.put("user_email", user.getEmail());

        final String subject = messageSource.getMessage("email.booking_notification.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} booking notification", restaurant.getEmail());
        sendMail(restaurant.getEmail(), subject, "booking-notification.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendBookingConfirmation(Booking booking, Locale locale) {
        Restaurant restaurant = booking.getRest();
        User user = booking.getBooker();

        final String url = appBaseUrl.toString();
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("date", formatter.format(booking.getDate().toString()));
        mailMap.put("hour", booking.getTime());
        mailMap.put("diners", String.valueOf(booking.getDiners()));
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("user", user.getUsername());
        mailMap.put("restaurant_email", restaurant.getEmail());

        final String subject = messageSource.getMessage("email.booking_confirmation.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} booking confirmation", user.getEmail());
        sendMail(user.getEmail(), subject, "booking-confirmation.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendBookingCancellationToRest(Booking booking, Locale locale) {
        Restaurant restaurant = booking.getRest();
        User user = booking.getBooker();

        final String url = appBaseUrl.toString();
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("date", formatter.format(booking.getDate().toString()));
        mailMap.put("hour", booking.getTime());
        mailMap.put("diners", String.valueOf(booking.getDiners()));
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("user", user.getUsername());

        final String subject = messageSource.getMessage("email.booking_cancellation.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} booking cancellation", restaurant.getEmail());
        sendMail(restaurant.getEmail(), subject, "booking-cancellation-to-rest.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendBookingCancellationToUser(Booking booking, Locale locale) {
        Restaurant restaurant = booking.getRest();
        User user = booking.getBooker();

        final String url = appBaseUrl.toString();
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("url", url);
        mailMap.put("date", formatter.format(booking.getDate().toString()));
        mailMap.put("hour", booking.getTime());
        mailMap.put("diners", String.valueOf(booking.getDiners()));
        mailMap.put("restaurant", restaurant.getFullName());
        mailMap.put("restaurant_email", restaurant.getEmail());

        final String subject = messageSource.getMessage("email.booking_cancellation.subject", new Object[]{restaurant.getFullName()}, locale);
        LOGGER.debug("Sending to {} booking cancellation", user.getEmail());
        sendMail(user.getEmail(), subject, "booking-cancellation-to-user.html", mailMap, locale);
    }
}
