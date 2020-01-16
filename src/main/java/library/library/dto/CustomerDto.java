package library.library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Long id;

    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String login;

    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String firstName;

    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String lastName;

    @Email(groups = {Add.class})
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String email;

    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String phone;

    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String addres;

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Collection<Long> loansId;
}
