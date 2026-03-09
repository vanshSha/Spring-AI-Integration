package com.service;

import com.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookDataService {
    private final List<Book> bookData = new ArrayList<>();

    @Tool (name = "get_all_books", description = "Get all books for all authors")
    public List<Book> getAllBooks() {
        return bookData;
    }

    // method to get a books by its author
    @Tool(name = "get_books_by_author", description = "Get all books for a specific author")
    public List<Book> getBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : bookData) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    @PostConstruct
    public void loadBookData() {
        bookData.add(new Book(1L,"Vector database for beginners: Introduction to Vector Databases: What You Need to Know", "PRITESH MISTRY", null, 2025));
        bookData.add(new Book(2L,"Github Copilot for Java Developers", "PRITESH MISTRY", null, 2024));
        bookData.add(new Book(3L,"Github Copilot for Developers: tips and tricks", "PRITESH MISTRY", null, 2024));
        bookData.add(new Book(4L,"Spring Boot in Action", "Craig Walls", null, 2016));
        bookData.add(new Book(5L,"Effective Java", "Joshua Bloch", null, 2018));
    }

}
