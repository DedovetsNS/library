package library.library.service;

import library.library.model.Loan;
import library.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    @Autowired
    private  LoanRepository loanRepository;
    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  BookService bookService;

    public Loan add(Loan loan) {
        if (!customerService.existByLogin(loan.getCustomer().getLogin())) {
            throw new IllegalArgumentException("User with such login does not exist.");
        }
        // TODO: 16.01.2020 наверное тут нужна транзакция
        bookService.giveBookToLoan(loan.getBook().getName(), loan.getQuantity());
        return loanRepository.save(loan);
    }

    public List<Loan> findAll() {
       return loanRepository.findAll();
    }
}
