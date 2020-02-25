package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.LoanDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.model.Loan;
import library.service.LoanService;
import library.transformer.impl.LoanTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static library.log.dictionary.ControllerMessages.*;

@Slf4j
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
        loanDto = loanTransformer.toDto(loan);
        log.info(LOG_ADD_NEW, Loan.class.toString(), loanDto.toString());
        return loanDto;
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<LoanDto> findAll() {
        Set<Loan> loans = loanService.findAll();
        log.info(LOG_GET_ALL, Loan.class.toString());
        return loanTransformer.toDto(loans);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public LoanDto getById(@PathVariable("id") Long id) {
        Loan loan = loanService.findById(id);
        LoanDto loanDto = loanTransformer.toDto(loan);
        log.info(LOG_GET, Loan.class.toString(), loanDto.toString());
        return loanDto;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        loanService.returnLoan(id);
        log.info(LOG_DELETE_BY_ID, Loan.class.toString(), id);
    }


    @JsonView(Details.class)
    @GetMapping("/expired")
    public Set<LoanDto> findExpired() {
        Set<Loan> loans = loanService.getExpiredLoans();
        log.info(LOG_GET_SET_BY, Loan.class.toString(), "expired Date");
        return loanTransformer.toDto(loans);
    }
}
