package library.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Long id;

    @JsonView(Details.class)
    @NotNull
    private String name;

    @JsonView(Details.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull
    private Date birthday;

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Collection<BookInAuthorDto> books;
}
