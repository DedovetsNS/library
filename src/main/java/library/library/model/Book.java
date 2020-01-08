package library.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "publisher")
    private String publisher;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Collection<Author> authors;

    @OneToMany(mappedBy = "bookId", fetch = FetchType.EAGER)
    private Collection<Loan> loans;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "in_stock_quantity")
    private Integer in_stock_quantity;

    public Book(String name, String publisher, Integer totalQuantity) {
        this.name = name;
        this.publisher = publisher;
        this.totalQuantity = totalQuantity;
    }
}
