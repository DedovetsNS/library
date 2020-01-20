package library.library.service.impl;

import library.library.Exception.AlreadyExistException;
import library.library.Exception.BadRequestParametrException;
import library.library.Exception.EmptyDataBaseException;
import library.library.Exception.NotFoundByIdException;
import library.library.model.Customer;
import library.library.model.Loan;
import library.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.List;

@Service
public class CustomerServiceImpl implements library.library.service.CustomerService {
    private final CustomerRepository customerRepository;
    private  LoanServiceImpl loanService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setLoanService(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @Override
    public Customer add(Customer customer) {
        if (customerRepository.existsByLogin(customer.getLogin())) {
            throw new AlreadyExistException("Customer", "login", customer.getLogin());
        }
        return customerRepository.save(customer);
    }

    @Override
    public boolean existByLogin(String login) {
        return customerRepository.existsByLogin(login);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new EmptyDataBaseException("Customers");
        }
        return customers;
    }

    @Override
    public Customer findByLogin(String customerLogin) {
        return customerRepository.findByLogin(customerLogin);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundByIdException("Customer", id));
    }

    @Override
    public void deleteById(Long id) {
        Customer deletedCustomer = findById(id);
        Collection<Loan> loans = deletedCustomer.getLoans();

        for (Loan loan : loans) {
            if(loanService.existById(loan.getId())){
                throw new BadRequestParametrException("Cannot delete a customer who has loans.");
            }
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Customer update(Customer customer) {
        if (existByLogin(customer.getLogin())) {
            throw new AlreadyExistException("Customer", "login", customer.getLogin());
        }
        Customer updatableCustomer = findById(customer.getId());
        updatableCustomer.setLogin(customer.getLogin());
        updatableCustomer.setFirstName(customer.getFirstName());
        updatableCustomer.setLastName(customer.getLastName());
        updatableCustomer.setAddres(customer.getAddres());
        updatableCustomer.setPhone(customer.getPhone());
        updatableCustomer.setEmail(customer.getEmail());
        return customerRepository.save(updatableCustomer);
    }
}
