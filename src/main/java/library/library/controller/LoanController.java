package library.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.LoanDto;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import library.library.model.Loan;
import library.library.service.LoanService;
import library.library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private  LoanService loanService;
    @Autowired
    private  Transformer transformer;

    @JsonView(Details.class)
    @PostMapping("/add")
    public LoanDto add(@RequestBody @Validated({Add.class}) LoanDto loanDto) {
        Loan loan = transformer.fromLoanDtoToLoan(loanDto);
        loanService.add(loan);
        return transformer.fromLoanToLoanDto(loan);
    }

    @JsonView(Details.class)
    @GetMapping("/findAll")
    public List<LoanDto> findAll() {
        List<Loan> loans = loanService.findAll();
        List<LoanDto> loansDto = transformer.fromLoanToLoanDto(loans);
        return loansDto;
    }
}
