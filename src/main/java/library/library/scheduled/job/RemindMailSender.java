package library.library.scheduled.job;

import library.library.model.Loan;
import library.library.service.LoanService;
import library.library.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemindMailSender {
    private final EmailServiceImpl emailService;
    private final LoanService loanService;

    @Autowired
    public RemindMailSender(EmailServiceImpl emailService, LoanService loanService) {
        this.emailService = emailService;
        this.loanService = loanService;
    }

    @Scheduled(cron = "${library.remind.cron}")
    private void sendRemindMail() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<Loan> expiredLoans = loanService.getExpiredLoans();
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
