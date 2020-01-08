package library.library.controller;


import library.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    // TODO: 08.01.2020 сделать соленые пароли и вообще перенести на Spring security
    @Autowired
    CustomerService customerService;

    // TODO: 08.01.2020 Проверка введенных данных на адекватность и существование в базе
    @GetMapping("/registration")
    public String login(@RequestParam Map<String,String> regParams){

       return customerService.regNewCustomer(regParams);
    }

}