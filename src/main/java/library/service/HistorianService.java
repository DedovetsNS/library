package library.service;

import library.dto.CustomerDto;

import java.util.Set;

public interface HistorianService {
    Boolean checkHistorianAccess(String firstName, String lastName);
    
    Set<CustomerDto> findAllWithAccess();
}
