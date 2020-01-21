package library.controller;


import com.fasterxml.jackson.annotation.JsonView;
import library.dto.CustomerDto;
import library.dto.LoanDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Customer;
import library.model.Loan;
import library.service.CustomerService;
import library.transformer.CustomerTransformer;
import library.transformer.LoanTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerTransformer customerTransformer;
    private final LoanTransformer loanTransformer;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerTransformer customerTransformer, LoanTransformer loanTransformer) {
        this.customerService = customerService;
        this.customerTransformer = customerTransformer;
        this.loanTransformer = loanTransformer;
    }

    @PostMapping
    public CustomerDto add(@RequestBody @Validated(Add.class) CustomerDto customerDto) {
        Customer customer = customerTransformer.toCustomer(customerDto);
        customer = customerService.add(customer);
        return customerTransformer.toCustomerDto(customer);
    }

    @JsonView(Details.class)
    @GetMapping
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDto> customersDto = customerTransformer.toCustomerDto(customers);
        return customersDto;
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public CustomerDto getById(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        CustomerDto customerDto = customerTransformer.toCustomerDto(customer);
        return customerDto;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

    @JsonView(Details.class)
    @PutMapping
    public CustomerDto updateById(@RequestBody @Validated({Update.class}) CustomerDto customerDto) {
        Customer customer = customerTransformer.toCustomer(customerDto);
        customer = customerService.update(customer);
        return customerTransformer.toCustomerDto(customer);
    }

    @JsonView(Details.class)
    @GetMapping("{id}/loans")
    public List<LoanDto> getLoansById(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        List<Loan> loans = (List<Loan>) customer.getLoans();
        return loanTransformer.toLoanDto(loans);
    }
}