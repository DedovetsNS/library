package library.service.impl;

import library.dto.CustomerDto;
import library.exception.AlreadyExistException;
import library.exception.BadRequestParameterException;
import library.exception.NotFoundException;
import library.model.Customer;
import library.repository.CustomerRepository;
import library.repository.LoanRepository;
import library.service.HistorianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements library.service.CustomerService {
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final HistorianService historianService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, LoanRepository loanRepository, HistorianService historianService) {
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
        this.historianService = historianService;
    }

    @Transactional
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

    @Transactional
    @Override
    public Set<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByLogin(String customerLogin) {
        return customerRepository.findByLogin(customerLogin).orElseThrow(() -> new NotFoundException("Customer", "login", customerLogin));
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer", "id", id.toString()));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (loanRepository.existsByCustomerId(id)) {
            throw new BadRequestParameterException("Cannot delete a customer who has loans.");
        }
        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        if (existByLogin(customer.getLogin())) {
            throw new AlreadyExistException("Customer", "login", customer.getLogin());
        }
        Customer updatableCustomer = findById(customer.getId());
        updatableCustomer.setLogin(customer.getLogin());
        updatableCustomer.setFirstName(customer.getFirstName());
        updatableCustomer.setLastName(customer.getLastName());
        updatableCustomer.setAddress(customer.getAddress());
        updatableCustomer.setPhone(customer.getPhone());
        updatableCustomer.setEmail(customer.getEmail());
        return customerRepository.save(updatableCustomer);
    }

    @Override
    public Set<Customer> intersectByFullName(Set<Customer> customers, Set<CustomerDto> customersDto){
        Set<Customer> intersect = new HashSet<>();

        for (Customer customer : customers) {
            for (CustomerDto customerDto : customersDto) {
                if(customer.getFirstName().equals(customerDto.getFirstName())&&
                    customer.getLastName().equals(customerDto.getLastName())){
                    intersect.add(customer);
                }
            }
        }
        return intersect;
    }

    @Override
    public Set<Customer> findAllWithAccess() {
       Set<CustomerDto> withAccessCustomerDto = historianService.findAllWithAccess();
        return intersectByFullName(customerRepository.findAll(),withAccessCustomerDto);
    }
}
