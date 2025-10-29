package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.Book;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.http.SdkHttpMethod;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FileService  fileService;

    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();

        books.forEach(book -> {
            if(book.getImageFileName() != null && !book.getImageFileName().isEmpty()) {
                book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.GET));
            }
        });

        return books;
    }

    public Book getBook(String id) {
        Book book = bookRepository.findById(id);
        if(book.getImageFileName() != null && !book.getImageFileName().isEmpty()) {
            book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.GET));
        }
        return book;
    }

    public Book addBook(Book book) {
        book = bookRepository.save(book);
        book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.PUT));
        return book;
    }

    public Book updateBook(String idLivro, Book book) {
        boolean atualizarImagem = false;
        Book before = bookRepository.findById(idLivro);
        if(!before.getImageFileName().equals(book.getImageFileName())) {
            atualizarImagem = true;
        }

        book = bookRepository.update(book);
        if(atualizarImagem) {
            book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.PUT));
        }

        return book;
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
