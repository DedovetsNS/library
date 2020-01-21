package library.transformer;

import library.dto.CustomerDto;
import library.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerTransformer {

    public Customer toCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .id(customerDto.getId())
                .login(customerDto.getLogin())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .addres(customerDto.getAddres())
                .build();
        return customer;
    }

    public CustomerDto toCustomerDto(Customer customer) {
        List<Long> loansId = new ArrayList<>();
        if (customer.getLoans() != null) {
            customer.getLoans().forEach(loan -> loansId.add(loan.getId()));
        }

        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .addres(customer.getAddres())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .loansId(loansId)
                .build();
        return customerDto;
    }

    public List<CustomerDto> toCustomerDto(List<Customer> customer) {
        List<CustomerDto> customerDto = new ArrayList<>();
        customer.stream().forEach(customer1 -> customerDto.add(toCustomerDto(customer1)));
        return customerDto;
    }
}
