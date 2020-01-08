package library.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    // TODO: 06.01.2020 Сделать дату формата даты, а не строки
    @Column(name = "birthday")
    private String birthday;

    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;

    public Author(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
