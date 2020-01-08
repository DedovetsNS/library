package library.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Entity
@Table(name = "loan")
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="book_id")
    private Book bookId;

    @Column(name ="quantity")
    private Integer quantity;

    @Column(name ="date")
    private Date date;

}
