package library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class AuthorResponseDto {

    private Long id;
    private String name;
    private String birthday;
    private Collection<BookInAuthorDto> books;
}
