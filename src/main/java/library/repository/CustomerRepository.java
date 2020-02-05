package library.repository;

import library.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Set<Customer> findAll();

    Boolean existsByLogin(String login);

    Optional<Customer> findByLogin(String login);

}