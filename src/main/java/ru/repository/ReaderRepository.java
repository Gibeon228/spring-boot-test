package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Reader;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Void deleteReaderById(Long id);
}
