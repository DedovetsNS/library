package library.library.service;

import library.library.model.Book;
import library.library.model.Loan;
import library.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class LoanService {
    // TODO: 08.01.2020 что с автовайрингом через конструктор?
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    BookService bookService;

    public String give(Map<String, String> loanParams) {
        String login = loanParams.get("login");
        String bookName = loanParams.get("bookName");
        Integer quantity = Integer.valueOf(loanParams.get("quantity"));


        if(!customerService.existByLogin(login)){
            return "User with such login does not exist.";
        }

        if(!bookService.existByName(bookName)){
            return "Book with such name does not exist.";
        }
// TODO: 08.01.2020 два раза хожу в база, проверить количество и существование книги в одном if-е
        if(bookService.getQuantityInStockByName(bookName)< quantity){
            return "Less books in the library than you want to take.";
        }
        // TODO: 08.01.2020 сделать транзакцию при походе в базу
        Loan newLoan = Loan.builder()
                .customerId(customerService.getByLogin(login))
                .bookId(bookService.getByName(bookName))
                .quantity(quantity)
                .date(new Date())
                .build();

        bookService.giveBook(bookName,quantity);
        customerService.addLoan(login,newLoan);
        loanRepository.save(newLoan);
        return "Book issued successfully.";
    }
}
