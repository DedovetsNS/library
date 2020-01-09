package library.library.controller;

import library.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @GetMapping("/add")
    public String add(@RequestParam Map<String, String> loanParams) {

        return loanService.add(loanParams);
    }

}
