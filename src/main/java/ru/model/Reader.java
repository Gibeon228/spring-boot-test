package ru.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "reader")
@Data
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public static Reader ofName(String name) {
        Reader reader = new Reader();
        reader.setName(name);
        return reader;
    }
}
