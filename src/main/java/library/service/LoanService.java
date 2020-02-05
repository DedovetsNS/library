package library.service;

import library.model.Loan;

import java.util.Set;

public interface LoanService {
    Loan takeLoan(Loan loan);

    void returnLoan(Long id);

    Set<Loan> findAll();

    Loan findById(Long id);

    void deleteById(Long id);

    Set<Loan> getExpiredLoans();

    Set<Loan> getLoansByCustomerId(Long id);
}
