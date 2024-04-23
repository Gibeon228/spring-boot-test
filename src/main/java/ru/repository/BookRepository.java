package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
