package library.repository;

import library.model.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    Set<Loan> findAll();

    Boolean existsByBookId(Long id);

    Set<Loan> findAllByCustomerId(Long id);

    Boolean existsByCustomerId(Long id);

    Set<Loan> findByCustomerId(Long id);
}
