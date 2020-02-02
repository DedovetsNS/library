package library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    @NotNull(groups = Update.class)
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
    @Null(groups = {Update.class})
    private Set<BookInAuthorDto> books;
}
