package ru.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.api.BookResponse;
import ru.model.Book;
import ru.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Optional<BookResponse> findById(Long id) {
        return bookRepository.findById(id).map(this::map);
    }

    private BookResponse map(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        return bookResponse;
    }
}
