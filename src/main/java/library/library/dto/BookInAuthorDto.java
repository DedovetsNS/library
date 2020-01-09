package library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInAuthorDto {

    private Long id;
    private String name;
    private String publisher;
}
