package com.example.apicoding1;

import com.example.apicoding1.entity.books;
import com.example.apicoding1.repo.BooksRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  
@Rollback
public class BooksControllerTest {

    @Autowired
    private BooksRepository brepo;

    @Test
    public void testAddBook() {
        books book = new books(101, "Java Core", "Shantanu", 2023);
        books saved = brepo.save(book);
        assertNotNull(saved);
        assertEquals("Java Core", saved.getTitle());
    }

    @Test
    public void testGetAllBooks() {
        books book1 = new books(201, "Spring Boot", "John", 2022);
        books book2 = new books(202, "ReactJS", "Doe", 2021);
        brepo.save(book1);
        brepo.save(book2);

        List<books> booksList = brepo.findAll();
        assertTrue(booksList.size() >= 2);
    }

    @Test
    public void testGetBookById() {
        books book = new books(301, "Kotlin", "Alex", 2024);
        brepo.save(book);

        Optional<books> found = brepo.findById(301);
        assertTrue(found.isPresent());
        assertEquals("Kotlin", found.get().getTitle());
    }

    @Test
    public void testUpdateBook() {
        books book = new books(401, "Old Title", "Author", 2020);
        brepo.save(book);

        books updated = brepo.findById(401).get();
        updated.setTitle("New Title");
        brepo.save(updated);

        books result = brepo.findById(401).get();
        assertEquals("New Title", result.getTitle());
    }

    @Test
    public void testDeleteBook() {
        books book = new books(501, "Delete Me", "Author", 2019);
        brepo.save(book);

        brepo.deleteById(501);
        Optional<books> deleted = brepo.findById(501);
        assertFalse(deleted.isPresent());
    }
}
