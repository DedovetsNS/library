package library.service;

import library.model.Customer;

import java.util.Set;

public interface CustomerService {
    Customer add(Customer customer);

    boolean existByLogin(String login);

    Set<Customer> findAll();

    Customer findByLogin(String customerLogin);

    Customer findById(Long id);

    void deleteById(Long id);

    Customer update(Customer customer);
}
