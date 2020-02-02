package library.scheduled.job;

import library.model.Loan;
import library.service.EmailService;
import library.service.LoanService;
import library.service.impl.EmailServiceImpl;
import library.service.impl.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RemindMailSender {
    private final EmailService emailService;
    private final LoanService loanService;

    @Autowired
    public RemindMailSender(EmailServiceImpl emailService, LoanServiceImpl loanService) {
        this.emailService = emailService;
        this.loanService = loanService;
    }

    @Scheduled(cron = "${library.remind.cron}")
    private void sendRemindMail() {
        Set<Loan> expiredLoans = loanService.getExpiredLoans();
        for (Loan loan : expiredLoans) {
            String mail = loan.getCustomer().getEmail();
            String name = loan.getCustomer().getFirstName() + " " + loan.getCustomer().getLastName();
            String bookName = loan.getBook().getName();
            String message = String.format("Hello dear %s, remind you that you have taken book " +
                    "%s from the library and its expiration date has ended ", name, bookName);
            emailService.sendSimpleMessage(mail, "Library reminder", message);
        }
    }
}
