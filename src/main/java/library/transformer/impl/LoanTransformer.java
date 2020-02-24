package library.transformer.impl;

import library.dto.LoanDto;
import library.model.Loan;
import library.service.CustomerService;
import library.service.impl.BookServiceImpl;
import library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoanTransformer implements Transformer<Loan, LoanDto> {
    private final CustomerService customerService;
    private final BookServiceImpl bookService;

    @Autowired
    public LoanTransformer(CustomerService customerService, BookServiceImpl bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Override
    public Loan toEntity(LoanDto loanDto) {
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

    @Override
    public LoanDto toDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        loanDto.setId(loan.getId());
        loanDto.setCustomerLogin(loan.getCustomer().getLogin());
        loanDto.setBookName(loan.getBook().getName());
        loanDto.setQuantity(loan.getQuantity());
        loanDto.setDate(loan.getDate());
        return loanDto;
    }

    @Override
    public Set<LoanDto> toDto(Set<Loan> loans) {
        Set<LoanDto> loansDto = new HashSet<>();

        loans.forEach(loan -> loansDto.add(toDto(loan)));
        return loansDto;
    }
}
