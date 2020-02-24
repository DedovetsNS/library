package library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Long id;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String login;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String firstName;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String lastName;

    @NotNull(groups = Update.class)
    @Email(groups = {Add.class})
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String email;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String phone;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    private String address;

    @Null(groups = Update.class)
    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Set<Long> loansId;
}
