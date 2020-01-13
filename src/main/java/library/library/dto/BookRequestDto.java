package library.library.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class BookRequestDto {

    @NotNull
    private String name;
    private String publisher;
    private Integer totalQuantity;
    private Integer in_stock_quantity;
    private Collection<AuthorInBookDto> authors;
}
