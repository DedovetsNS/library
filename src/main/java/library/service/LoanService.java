package library.service;

import library.model.Loan;

import java.util.List;

public interface LoanService {
    Loan takeLoan(Loan loan);

    void returnLoan(Long id);

    List<Loan> findAll();

    Loan findById(Long id);

    void deleteById(Long id);

    boolean existById(Long id);

    List<Loan> getExpiredLoans();
}