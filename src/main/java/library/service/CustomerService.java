package library.service;

import library.dto.CustomerDto;
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

    Set<Customer> intersectByFullName(Set<Customer> customers, Set<CustomerDto> customersDto);

    Set<Customer> findAllWithAccess();
}
