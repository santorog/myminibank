package org.example.myminibank.events;

import org.example.myminibank.model.Account;

import java.util.List;

public class NotificationEvent {

    private String email;
    private String subject;
    private String message;

    public NotificationEvent() {
    }

    public NotificationEvent(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public static List<NotificationEvent> transferNotificationEvents(
            Account fromAccount,
            Account toAccount,
            double amount) {

        return List.of(
                buildTransferConfirmationEmail(fromAccount.getEmail(), amount, toAccount.getName()),
                buildTransferReceivedEmail(toAccount.getEmail(), amount, fromAccount.getName()));
    }

    public static NotificationEvent buildTransferConfirmationEmail(String email, double amount, String toName) {
        String subject = "Confirmation de votre virement";
        String message = """
        Bonjour,

        Votre virement de %.2f EUR a bien été effectué a %s.

        Merci de votre confiance,
        L’équipe MyMiniBank
        """.formatted(amount, toName);

        return new NotificationEvent(email, subject, message);
    }

    public static NotificationEvent buildTransferReceivedEmail(String email, double amount, String fromName) {
        String subject = "Vous avez reçu un virement";
        String message = """
        Bonjour,

        Vous avez reçu %.2f EUR sur votre compte de la part de %s.

        Pensez à vérifier votre solde dans votre espace personnel.

        L’équipe MyMiniBank
        """.formatted(amount, fromName);

        return new NotificationEvent(email, subject, message);
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

}
