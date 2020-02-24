package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.LoanDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.model.Loan;
import library.service.LoanService;
import library.transformer.impl.LoanTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
        Loan loan = loanTransformer.toEntity(loanDto);
        loanService.takeLoan(loan);
        return loanTransformer.toDto(loan);
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<LoanDto> findAll() {
        Set<Loan> loans = loanService.findAll();
        return loanTransformer.toDto(loans);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public LoanDto getById(@PathVariable("id") Long id) {
        Loan loan = loanService.findById(id);
        return loanTransformer.toDto(loan);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        loanService.returnLoan(id);
    }


    @JsonView(Details.class)
    @GetMapping("/expired")
    public Set<LoanDto> findExpired() {
        Set<Loan> loans = loanService.getExpiredLoans();
        return loanTransformer.toDto(loans);
    }
}
