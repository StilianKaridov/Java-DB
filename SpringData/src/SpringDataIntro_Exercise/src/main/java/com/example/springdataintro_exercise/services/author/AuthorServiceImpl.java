package com.example.springdataintro_exercise.services.author;

import com.example.springdataintro_exercise.domain.enums.Constant;
import com.example.springdataintro_exercise.domain.models.Author;
import com.example.springdataintro_exercise.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void add(Author author) {
        this.authorRepository.save(author);
    }

    @Override
    public Author getRandomAuthor() {
        int count = (int) this.authorRepository.count();

        Random random = new Random();
        int authorID = random.nextInt(count) + 1;

        return this.authorRepository.getAuthorById(authorID);
    }

    @Override
    public Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990() {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(Constant.YEAR_BEFORE);
    }

    @Override
    public List<Author> getAllAuthorsOrderedByBookCountDesc() {
        List<Author> authors = this.authorRepository.findAll();

        return authors
                .stream()
                .sorted((a1, a2) -> a2.getBooks().size() - a1.getBooks().size())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }
}
