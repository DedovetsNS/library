package library.transformer;

import library.dto.CustomerDto;
import library.model.Customer;
import library.model.Loan;
import library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerTransformer {

    private final LoanRepository loanRepository;

    @Autowired
    public CustomerTransformer(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Customer toCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .login(customerDto.getLogin())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .addres(customerDto.getAddres())
                .build();
    }

    public CustomerDto toCustomerDto(Customer customer) {
        Set<Loan> loans = loanRepository.findAllByCustomerId(customer.getId());
        Set<Long> loansId = loans.stream().map(Loan::getId).collect(Collectors.toSet());

        return CustomerDto.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .addres(customer.getAddres())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .loansId(loansId)
                .build();
    }

    public Set<CustomerDto> toCustomerDto(Set<Customer> customer) {
        Set<CustomerDto> customerDto = new HashSet<>();
        customer.forEach(customer1 -> customerDto.add(toCustomerDto(customer1)));
        return customerDto;
    }
}
