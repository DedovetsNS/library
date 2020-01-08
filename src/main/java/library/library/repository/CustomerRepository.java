package library.library.repository;

import library.library.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();
    Boolean existsByLogin(String login);
    Customer findByLogin(String login);
}