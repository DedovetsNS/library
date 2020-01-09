package library.library.service;

import library.library.model.Customer;
import library.library.model.Loan;
import library.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public String regNewCustomer(Map<String, String> regParams) {
        // TODO: 08.01.2020 добавить аналогичную проверку по другим полям
        if (customerRepository.existsByLogin(regParams.get("login"))) {
            return "Сlient with that login already exists.";
        }

        Customer newRegCustomer = Customer.builder()
                .login(regParams.get("login"))
                .firstName(regParams.get("firstName"))
                .lastName(regParams.get("lastName"))
                .email(regParams.get("email"))
                .phone(regParams.get("phone"))
                .addres(regParams.get("addres"))
                .build();
        customerRepository.save(newRegCustomer);
        return "New customer successfully  added.";
    }

    boolean existByLogin(String login) {
        return customerRepository.existsByLogin(login);
    }

    Customer getByLogin(String login) {
        return customerRepository.findByLogin(login);
    }

    public void addLoan(String login, Loan loan) {
        Customer borrower = customerRepository.findByLogin(login);
        borrower.getLoans().add(loan);
        customerRepository.save(borrower);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
