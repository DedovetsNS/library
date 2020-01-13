package library.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // TODO: 06.01.2020 Сделать дату формата даты, а не строки
    private String birthday;

//    @Column(updatable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime creationDate;

    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;

    public Author(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
        this.books = new ArrayList<Book>();
    }
}
