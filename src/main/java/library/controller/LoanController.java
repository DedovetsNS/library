package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.LoanDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.model.Loan;
import library.service.LoanService;
import library.transformer.LoanTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;
    private final LoanTransformer loanTransformer;

    @Autowired
    public LoanController(LoanService loanService, LoanTransformer loanTransformer) {
        this.loanService = loanService;
        this.loanTransformer = loanTransformer;
    }

    @JsonView(Details.class)
    @PostMapping
    public LoanDto add(@RequestBody @Validated({Add.class}) LoanDto loanDto) {
        Loan loan = loanTransformer.toLoan(loanDto);
        loanService.takeLoan(loan);
        return loanTransformer.toLoanDto(loan);
    }

    @JsonView(Details.class)
    @GetMapping
    public List<LoanDto> findAll() {
        List<Loan> loans = loanService.findAll();
        return loanTransformer.toLoanDto(loans);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public LoanDto getById(@PathVariable("id") Long id) {
        Loan loan = loanService.findById(id);
        return loanTransformer.toLoanDto(loan);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        loanService.returnLoan(id);
    }


    @JsonView(Details.class)
    @GetMapping("/expired")
    public List<LoanDto> findExpired() {
        List<Loan> loans = loanService.getExpiredLoans();
        return loanTransformer.toLoanDto(loans);
    }
}
