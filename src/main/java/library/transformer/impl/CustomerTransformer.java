package library.transformer.impl;

import library.dto.CustomerDto;
import library.model.Customer;
import library.model.Loan;
import library.repository.LoanRepository;
import library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerTransformer implements Transformer<Customer, CustomerDto> {

    private final LoanRepository loanRepository;

    @Autowired
    public CustomerTransformer(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .login(customerDto.getLogin())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .address(customerDto.getAddress())
                .build();
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        Set<Loan> loans = loanRepository.findAllByCustomerId(customer.getId());
        Set<Long> loansId = loans.stream().map(Loan::getId).collect(Collectors.toSet());

        return CustomerDto.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .loansId(loansId)
                .build();
    }

}
