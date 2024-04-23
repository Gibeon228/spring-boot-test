package ru.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.JUnitSpringBootBase;
import ru.model.Reader;
import ru.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;



class ReaderControllerTest extends JUnitSpringBootBase {

    @Data
    static class JUnitReaderResponse {
        private Long id;
        private String name;
    }

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    void testFindByIdNotFound() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);

        webTestClient.get()
                .uri("/api/reader/" + maxId +1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByIdSuccess() {
        // region Подготовка данных
        Reader expected = readerRepository.save(Reader.ofName("random"));
        // endregion

        JUnitReaderResponse responseBody = webTestClient.get()
                .uri("/api/reader/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReaderResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }


    @Test
    void testGetAll() {
        // region Подготовка данных
        readerRepository.saveAll(List.of(
            Reader.ofName("firstName"),
            Reader.ofName("secondName")
        ));
        // endregion

        List<Reader> expected = readerRepository.findAll();

        List<JUnitReaderResponse> responsesBody = webTestClient.get()
                .uri("/api/reader")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responsesBody.size());
        for (JUnitReaderResponse readerResponse : responsesBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), readerResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), readerResponse.getName()));
            Assertions.assertTrue(found);
        }



    }


}