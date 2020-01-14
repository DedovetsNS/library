package library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String addres;
    private Collection<Long> loansId;
}
