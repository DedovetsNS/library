package library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @Null(groups = {Add.class})
    private Long id;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private String name;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private String publisher;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private Integer totalQuantity;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private Integer inStockQuantity;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private Set<AuthorInBookDto> authors;

    @NotNull(groups = Update.class)
    @JsonView(Details.class)
    @NotNull(groups = {Add.class})
    @NotNull
    private Boolean specificAccess;
}

