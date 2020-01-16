package library.library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Long id;

    @JsonView(Details.class)
    @NotNull
    private String name;

    @JsonView(Details.class)
    @NotNull
    private String publisher;

    @JsonView(Details.class)
    @NotNull
    private Integer totalQuantity;

    @JsonView(Details.class)
    private Integer in_stock_quantity;

    @JsonView(Details.class)
    @NotNull
    private Collection<AuthorInBookDto> authors;
}

