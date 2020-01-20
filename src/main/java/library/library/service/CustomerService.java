package library.library.service;

import library.library.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer add(Customer customer);

    boolean existByLogin(String login);

    List<Customer> findAll();

    Customer findByLogin(String customerLogin);

    Customer findById(Long id);

    void deleteById(Long id);

    Customer update(Customer customer);
}
