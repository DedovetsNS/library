package library.library.transformer;

import library.library.dto.LoanDto;
import library.library.model.Loan;
import library.library.service.CustomerService;
import library.library.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LoanTransformer {
    private final CustomerService customerService;
    private final BookServiceImpl bookService;

    @Autowired
    public LoanTransformer(CustomerService customerService, BookServiceImpl bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    public Loan toLoan(LoanDto loanDto) {
        Loan loan = new Loan();

        loan.setId(loanDto.getId());
        loan.setQuantity(loanDto.getQuantity());
        loan.setCustomer(customerService.findByLogin(loanDto.getCustomerLogin()));
        loan.setBook(bookService.findByName(loanDto.getBookName()));
        if (loanDto.getDate() == null) {
            loan.setDate(new Date());
        } else {
            loanDto.setDate(loanDto.getDate());
        }
        return loan;
    }

    public LoanDto toLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        loanDto.setId(loan.getId());
        loanDto.setCustomerLogin(loan.getCustomer().getLogin());
        loanDto.setBookName(loan.getBook().getName());
        loanDto.setQuantity(loan.getQuantity());
        loanDto.setDate(loan.getDate());
        return loanDto;
    }

    public List<LoanDto> toLoanDto(List<Loan> loans) {
        List<LoanDto> loansDto = new ArrayList<>();

        loans.forEach(loan -> loansDto.add(toLoanDto(loan)));
        return loansDto;
    }
}
