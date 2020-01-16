package library.library.service;

import library.library.model.Customer;
import library.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private  CustomerRepository customerRepository;

    public Customer regNewCustomer(Customer customer) {
        if (customerRepository.existsByLogin(customer.getLogin())) {
            throw new IllegalArgumentException("Сlient with that login already exists.");
        }
        return customerRepository.save(customer);
    }

    boolean existByLogin(String login) {
        return customerRepository.existsByLogin(login);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findByLogin(String customerLogin) {
        return customerRepository.findByLogin(customerLogin);
    }
}
