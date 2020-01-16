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
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInBookDto {

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    @NotNull(groups = {Details.class})
    private Long id;

    @JsonView(Details.class)
    @NotNull
    private String name;

    @JsonView(Details.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(groups = {Details.class})
    private Date birthday;
}
