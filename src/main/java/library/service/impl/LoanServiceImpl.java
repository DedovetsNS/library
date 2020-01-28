package library.service.impl;

import library.exception.NotFoundByIdException;
import library.exception.NotFoundException;
import library.model.Loan;
import library.repository.LoanRepository;
import library.service.BookService;
import library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements library.service.LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final BookService bookService;

    @Value("${library.loanconfig.day}")
    private String delayDays;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, CustomerService customerService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Loan takeLoan(Loan loan) {
        String customerLogin = loan.getCustomer().getLogin();
        if (!customerService.existByLogin(customerLogin)) {
            throw new NotFoundException("Customer", "login", customerLogin);
        }
        bookService.takeBookToLoan(loan.getBook().getName(), loan.getQuantity());
        return loanRepository.save(loan);
    }

    @Transactional
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

    @Transactional
    @Override
    public List<Loan> getExpiredLoans() {
        List<Loan> loans = loanRepository.findAll();
        List<Loan> expiredLoans = new ArrayList<>();

        for (Loan loan : loans) {
            LocalDate takeDate = loan.getDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate returnDate = takeDate.plusDays(Long.valueOf(delayDays));
            LocalDate nowDate = LocalDate.now();
            if (returnDate.isBefore(nowDate)) {
                expiredLoans.add(loan);
            }
        }
        return expiredLoans;
    }
}
