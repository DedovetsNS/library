package library.library.dto;

import library.library.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@AllArgsConstructor
@Data
public class BookResponseDto implements Comparable<BookResponseDto> {


    private Long id;
    private String name;
    private String publisher;
    private Integer totalQuantity;
    private Integer in_stock_quantity;
    private Collection<AuthorInBookDto> authors;

    @Override
    public int compareTo(BookResponseDto o) {
        return this.id.compareTo(o.id);
    }
}

