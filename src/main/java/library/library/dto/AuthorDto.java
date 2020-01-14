package library.library.dto;

import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    @Null(groups = {Add.class})
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String birthday;

    @Null(groups = {Add.class})
    private Collection<BookInAuthorDto> books;
}
