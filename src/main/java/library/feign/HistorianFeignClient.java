package library.feign;

import library.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(url = "${historian.checker.url}",name = "historianChecker")
public interface HistorianFeignClient {

    @GetMapping("historian/checkAccess/{firstName}/{lastName}")
    Boolean checkAccess(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName);

    @GetMapping("historian/AllWithAccess")
    Set<CustomerDto> findAllWithAccess();
}
