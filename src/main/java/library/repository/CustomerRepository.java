package library.repository;

import library.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    Boolean existsByLogin(String login);

    Optional<Customer> findByLogin(String login);

}