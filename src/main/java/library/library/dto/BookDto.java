package library.library.dto;

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

    @Null(groups = {Add.class})
    @NotNull(groups = {Details.class})
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String publisher;

    @NotNull
    private Integer totalQuantity;

    @NotNull(groups = {Details.class})
    private Integer in_stock_quantity;

    @NotNull
    private Collection<AuthorInBookDto> authors;
}
