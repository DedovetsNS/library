package library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import library.dto.groups.Add;
import library.dto.groups.Details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {

    @JsonView(Details.class)
    @Null(groups = Add.class)
    private Long id;

    @JsonView(Details.class)
    @NotNull(groups = Add.class)
    private String customerLogin;

    @JsonView(Details.class)
    @NotNull(groups = Add.class)
    private String bookName;

    @JsonView(Details.class)
    @NotNull(groups = Add.class)
    private Integer quantity;

    @JsonView(Details.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
}
