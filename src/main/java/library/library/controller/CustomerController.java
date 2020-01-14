package library.library.controller;


import library.library.dto.CustomerDto;
import library.library.model.Customer;
import library.library.model.Loan;
import library.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    // TODO: 08.01.2020 сделать соленые пароли и вообще перенести на Spring security
    @Autowired
    CustomerService customerService;

    // TODO: 08.01.2020 Проверка введенных данных на адекватность и существование в базе
    @GetMapping("/reg")
    public String login(@RequestParam Map<String, String> regParams) {

        return customerService.regNewCustomer(regParams);
    }

    @GetMapping("/findAll")
    public List<CustomerDto> login() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDto> customersDto = new ArrayList<>();

        for (Customer customer : customers) {
            List<Long> loansIdDto = new ArrayList<>();
            Collection<Loan> loansId = customer.getLoans();
            for (Loan loan : loansId) {
                loansIdDto.add(loan.getId());
            }
            customersDto.add(CustomerDto.builder()
                    .id(customer.getId())
                    .login(customer.getLogin())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .addres(customer.getAddres())
                    .loansId(loansIdDto)
                    .build());
        }
        return customersDto;
    }

}