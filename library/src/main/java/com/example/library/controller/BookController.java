
package com.example.library.controller;

import com.example.library.model.Book;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    // Collection to store books
    private List<Book> books = new ArrayList<>();

    // Q2(b): Add book
    @PostMapping
    public ResponseEntity<Book> addBook(
            @RequestBody Book book) {

        // Check duplicate ID
        for (Book b : books) {
            if (b.getId().equals(book.getId())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .build();
            }
        }

        books.add(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }

    // Q2(c): Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(books);
    }

    // Q2(c): Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Integer id) {

        for (Book book : books) {

            if (book.getId().equals(id)) {
                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // Q2(d): Update book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {

            if (book.getId().equals(id)) {

                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());

                book.setAvailableCopies(
                        updatedBook.getAvailableCopies()
                );

                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // Q2(e): Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Integer id) {

        for (Book book : books) {

            if (book.getId().equals(id)) {

                books.remove(book);

                return ResponseEntity.ok(
                        "Book deleted successfully!"
                );
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found!");
    }
}
