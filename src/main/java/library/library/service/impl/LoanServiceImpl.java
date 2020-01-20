package library.library.service.impl;

import library.library.Exception.NotFoundByIdException;
import library.library.Exception.NotFoundException;
import library.library.model.Loan;
import library.library.repository.LoanRepository;
import library.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
