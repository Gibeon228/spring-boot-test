package ru.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.api.ReaderResponse;
import ru.model.Reader;
import ru.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;
    public List<ReaderResponse> getAll() {
        return readerRepository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }


    public Void deleteReaderById(@PathVariable Long id) {
        return readerRepository.deleteReaderById(id);
    }

    public Optional<ReaderResponse> findById(Long id) {
        return readerRepository.findById(id).map(this::map);
    }

    private ReaderResponse map(Reader reader) {
        ReaderResponse readerResponse = new ReaderResponse();
        readerResponse.setId(reader.getId());
        readerResponse.setName(reader.getName());
        return readerResponse;
    }
}
