package library.service.impl;

import library.dto.AuthorDto;
import library.exception.AlreadyExistException;
import library.exception.NotFoundException;
import library.model.Author;
import library.repository.AuthorRepository;
import library.repository.BookAuthorRepository;
import library.service.AuthorService;
import library.transformer.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorTransformer authorTransformer;
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorTransformer authorTransformer, BookAuthorRepository bookAuthorRepository) {
        this.authorRepository = authorRepository;
        this.authorTransformer = authorTransformer;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Transactional
    @Override
    public AuthorDto add(AuthorDto authorDto) {
        if (authorRepository.existsByName(authorDto.getName())) {
            throw new AlreadyExistException("Author", "name", authorDto.getName());
        } else {
            Author author = authorTransformer.toAuthor(authorDto);
            author = authorRepository.save(author);
            return authorTransformer.toAuthorDto(author);
        }
    }


    @Transactional
    @Override
    public Set<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author", "id", id.toString()));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookAuthorRepository.deleteAllByAuthorId(id);
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Author author = findById(authorDto.getId());

        author.setBirthday(authorDto.getBirthday());
        author.setName(authorDto.getName());
        return authorTransformer.toAuthorDto(authorRepository.save(author));
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> new NotFoundException("Author", "name", name));
    }
}
