package library.library.controller;


import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.CustomerDto;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import library.library.model.Customer;
import library.library.service.CustomerService;
import library.library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private Transformer transformer;

    @PostMapping("/reg")
    public CustomerDto reg(@RequestBody @Validated(Add.class) CustomerDto customerDto) {
        Customer customer = transformer.fromCustomerDtoToCustomer(customerDto);
        customer = customerService.regNewCustomer(customer);
        return transformer.fromCustomerToCustomerDto(customer);
    }

    @JsonView(Details.class)
    @GetMapping("/findAll")
    public List<CustomerDto> login() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDto> customersDto = transformer.fromCustomerToCustomerDto(customers);
        return customersDto;
    }
}