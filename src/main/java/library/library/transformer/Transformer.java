package library.library.transformer;

import library.library.dto.*;
import library.library.model.Author;
import library.library.model.Book;
import library.library.model.Customer;
import library.library.model.Loan;
import library.library.service.BookService;
import library.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class Transformer {
    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  BookService bookService;

    public Book fromBookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        List<Author> authors = new ArrayList<>();

        for (AuthorInBookDto authorDto : bookDto.getAuthors()) {
            Author author = new Author();
            author.setName(authorDto.getName());
            author.setBirthday(authorDto.getBirthday());
            author.setBooks(new ArrayList<Book>());
            author.getBooks().add(book);
            authors.add(author);
        }

        book.setName(bookDto.getName());
        book.setPublisher(bookDto.getPublisher());
        book.setTotalQuantity(bookDto.getTotalQuantity());
        book.setIn_stock_quantity(bookDto.getIn_stock_quantity());
        book.setAuthors(authors);

        return book;
    }

    public BookDto fromBookToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        Collection<Author> authors = book.getAuthors();
        List<AuthorInBookDto> authorsInBookDto = new ArrayList<>();

        for (Author author : authors) {
            authorsInBookDto.add(new AuthorInBookDto(author.getId(), author.getName(), author.getBirthday()));
        }

        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setAuthors(authorsInBookDto);
        bookDto.setTotalQuantity(book.getTotalQuantity());
        bookDto.setIn_stock_quantity(book.getIn_stock_quantity());

        return bookDto;
    }

    public Author fromAuthorDtoToAuthor(AuthorDto authorDto) {
        Author author = new Author();

        author.setName(authorDto.getName());
        author.setBirthday(authorDto.getBirthday());

        return author;
    }

    public List<BookDto> fromBookToBookDto(List<Book> allBooks) {
        List<BookDto> allBooksDto = new ArrayList<>();

        for (Book book : allBooks) {
            BookDto bookDto = fromBookToBookDto(book);
            allBooksDto.add(bookDto);
        }
        return allBooksDto;
    }

    public AuthorDto fromAuthorToAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        List<BookInAuthorDto> booksInAuthorDto = new ArrayList<>();
        Collection<Book> books = author.getBooks();

        if (books != null) {
            for (Book book : books) {
                booksInAuthorDto.add(new BookInAuthorDto(book.getId(), book.getName(), book.getPublisher()));
            }
        }
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }

    public List<AuthorDto> fromAuthorToAuthorDto(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        authors.forEach(author -> authorsDto.add(fromAuthorToAuthorDto(author)));
        return authorsDto;
    }

    public Customer fromCustomerDtoToCustomer(CustomerDto customerDto) {
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

    public CustomerDto fromCustomerToCustomerDto(Customer customer) {
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

    public List<CustomerDto> fromCustomerToCustomerDto(List<Customer> customer) {
        List<CustomerDto> customerDto = new ArrayList<>();
        customer.stream().forEach(customer1 -> customerDto.add(fromCustomerToCustomerDto(customer1)));
        return customerDto;
    }

    public Loan fromLoanDtoToLoan(LoanDto loanDto) {
        Loan loan = new Loan();

        loan.setId(loanDto.getId());
        loan.setQuantity(loanDto.getQuantity());
        loan.setCustomer(customerService.findByLogin(loanDto.getCustomerLogin()));
        loan.setBook(bookService.findByName(loanDto.getBookName()));
        if (loanDto.getDate() == null) {
            loan.setDate(new Date());
        } else {
            loanDto.setDate(loanDto.getDate());
        }
        return loan;
    }

    public LoanDto fromLoanToLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        loanDto.setId(loan.getId());
        loanDto.setCustomerLogin(loan.getCustomer().getLogin());
        loanDto.setBookName(loan.getBook().getName());
        loanDto.setQuantity(loan.getQuantity());
        loanDto.setDate(loan.getDate());
        return loanDto;
    }

    public List<LoanDto> fromLoanToLoanDto(List<Loan> loans) {
        List<LoanDto> loansDto = new ArrayList<>();

        loans.forEach(loan -> loansDto.add(fromLoanToLoanDto(loan)));
        return loansDto;
    }
}

