package library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInAuthorDto {

    @JsonView(Details.class)
    private Long id;

    @JsonView(Details.class)
    private String name;

    @JsonView(Details.class)
    private String publisher;
}
