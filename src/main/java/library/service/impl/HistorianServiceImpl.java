package library.service.impl;

import library.dto.CustomerDto;
import library.feign.HistorianFeignClient;
import library.service.HistorianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HistorianServiceImpl implements HistorianService {

    private final HistorianFeignClient historianFeignClient;

    @Autowired
    public HistorianServiceImpl(HistorianFeignClient historianFeignClient) {
        this.historianFeignClient = historianFeignClient;
    }

    @Override
    public Boolean checkHistorianAccess(String firstName, String lastName) {
        return historianFeignClient.checkAccess(firstName, lastName);
    }

    @Override
    public Set<CustomerDto> findAllWithAccess() {
        return historianFeignClient.findAllWithAccess();
    }


}
