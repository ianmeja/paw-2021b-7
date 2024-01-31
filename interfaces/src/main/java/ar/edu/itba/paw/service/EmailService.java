package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Review;

import java.util.Locale;

public interface EmailService {

    void sendUserVerificationEmail(String email, String token, Locale locale);
    void sendConfirmationEmail(String email, String name, Locale locale);
    void sendReviewNotification(Review review, Locale locale);
    void sendResponseNotification(Review review, String answer, Locale locale);
    void sendBookingInfo(Booking booking, Locale locale);
    void sendBookingNotification(Booking booking, Locale locale);
    void sendBookingConfirmation(Booking booking, Locale locale);
    void sendBookingCancellationToRest(Booking booking, Locale locale);
    void sendBookingCancellationToUser(Booking booking, Locale locale);
}
