package library.library.dto;

import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInBookDto {

    @Null(groups = {Add.class})
    @NotNull(groups = {Details.class})
    private Long id;

    @NotNull
    private String name;

    @NotNull(groups = {Details.class})
    private String birthday;
}
