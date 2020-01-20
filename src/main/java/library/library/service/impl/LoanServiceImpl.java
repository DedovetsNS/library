package library.library.service.impl;

import library.library.Exception.NotFoundByIdException;
import library.library.Exception.NotFoundException;
import library.library.model.Loan;
import library.library.repository.LoanRepository;
import library.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class LoanServiceImpl implements library.library.service.LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final BookServiceImpl bookService;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, CustomerService customerService, BookServiceImpl bookService) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Value("${library.loanconfig.day}")
    String days;

    @Override
    public Loan takeLoan(Loan loan) {
        String customerLogin = loan.getCustomer().getLogin();
        if (!customerService.existByLogin(customerLogin)) {
            throw new NotFoundException("Customer", "login", customerLogin);
        }
        bookService.takeBookToLoan(loan.getBook().getName(), loan.getQuantity());
        return loanRepository.save(loan);
    }

    @Override
    public void returnLoan(Long id) {
        Loan loan = findById(id);
        bookService.returnBook(loan.getBook().getName(), loan.getQuantity());
        loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new NotFoundByIdException("Loan", id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        loanRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
       return loanRepository.existsById(id);
    }

    @Override
    public List<Loan> getExpiredLoans(){
        List<Loan> loans = loanRepository.findAll();
        List<Loan> expiredLoans = new ArrayList<>();

        for (Loan loan : loans) {
        LocalDate takeDate = loan.getDate().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate returnDate = takeDate.plusDays(Long.valueOf(days));
        LocalDate nowDate = LocalDate.now();
            if(returnDate.isBefore(nowDate)){
            expiredLoans.add(loan);
            }
        }
        return expiredLoans;
    }
}
