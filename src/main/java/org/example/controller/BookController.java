package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(),  HttpStatus.OK);
    }

    @GetMapping(value = "/{idLivro}", produces = "application/json")
    public ResponseEntity<Book> getBook(@PathVariable String idLivro) {
        return new ResponseEntity<>(bookService.getBook(idLivro), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idLivro}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Book> updateBook(@Valid @PathVariable String idLivro, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(idLivro, book), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idLivro}")
    public ResponseEntity<Void> deleteBook(@PathVariable String idLivro) {
        bookService.deleteBook(idLivro);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
