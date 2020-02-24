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
import library.service.LoanService;
import library.transformer.impl.CustomerTransformer;
import library.transformer.impl.LoanTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static library.log.dictionary.ControllerMessages.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerTransformer customerTransformer;
    private final LoanTransformer loanTransformer;
    private final LoanService loanService;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerTransformer customerTransformer, LoanTransformer loanTransformer, LoanService loanService) {
        this.customerService = customerService;
        this.customerTransformer = customerTransformer;
        this.loanTransformer = loanTransformer;
        this.loanService = loanService;
    }

    @PostMapping
    public CustomerDto add(@RequestBody @Validated(Add.class) CustomerDto customerDto) {
        Customer customer = customerTransformer.toEntity(customerDto);
        customer = customerService.add(customer);
        log.info(LOG_ADD_NEW,Customer.class.toString(),customer.toString());
        return customerTransformer.toDto(customer);
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<CustomerDto> findAll() {
        Set<Customer> customers = customerService.findAll();
        log.info(LOG_GET_ALL,Customer.class.toString());
        return customerTransformer.toDto(customers);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public CustomerDto getById(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        log.info(LOG_GET,Customer.class.toString(),customer.toString());
        return customerTransformer.toDto(customer);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        log.info(LOG_DELETE_BY_ID,Customer.class.toString(),id);
    }

    @JsonView(Details.class)
    @PutMapping
    public CustomerDto updateById(@RequestBody @Validated({Update.class}) CustomerDto customerDto) {
        Customer customer = customerTransformer.toEntity(customerDto);
        customer = customerService.update(customer);
        log.info(LOG_UPDATE,Customer.class.toString(),customer.toString());
        return customerTransformer.toDto(customer);
    }

    @JsonView(Details.class)
    @GetMapping("{id}/loans")
    public Set<LoanDto> getLoansById(@PathVariable("id") Long id) {
        Set<Loan> loans = loanService.getLoansByCustomerId(id);
        log.info(LOG_GET_SET_BY,Loan.class.toString(),"Customer id="+id);
        return loanTransformer.toDto(loans);
    }

    @JsonView(Details.class)
    @GetMapping("/withAccess")
    public Set<CustomerDto> findAllWithAccess() {
        Set<Customer> customers = customerService.findAllWithAccess();
        log.info(LOG_GET_SET_BY,Customer.class.toString(),"Customer have Specific Access");
        return customerTransformer.toDto(customers);
    }
}