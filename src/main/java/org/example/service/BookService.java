package org.example.service;

import org.example.dto.Book;
import org.example.dto.PagedResult;
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
    private FileService fileService;

    @Autowired
    private UserService userService;

    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();

        books.forEach(book -> {
            book.setUserID(null);
            if(book.getImageFileName() != null && !book.getImageFileName().isEmpty()) {
                book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.GET));
            }
        });

        return books;
    }

    public Book getBook(String id) {
        Book book = bookRepository.findById(id);
        book.setUserID(null);
        if(book.getImageFileName() != null && !book.getImageFileName().isEmpty()) {
            book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.GET));
        }
        return book;
    }

    public Book addBook(Book book, String email) {
        if(!userService.existsByEmail(email)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        book.setUserID(email);
        book = bookRepository.save(book);
        userService.addBookToUser(email, book.getId());
        book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.PUT));
        return book;
    }

    public Book updateBook(String idLivro, Book book) {
        boolean atualizarImagem = false;
        Book before = bookRepository.findById(idLivro);
        if(before == null) {
            throw new RuntimeException("Livro nao encontrado");
        }

        if(!before.getImageFileName().equals(book.getImageFileName())) {
            atualizarImagem = true;
        }

        // não permite alteração do dono do livro por esse método
        book.setUserID(before.getUserID());
        book = bookRepository.update(book);
        if(atualizarImagem) {
            book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.PUT));
        }

        return book;
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public PagedResult findBooks(String lastItem, int pageSize) {
        PagedResult pr = bookRepository.findBooksPage(lastItem, pageSize);
        pr.getBooks().forEach(book -> {
            book.setUserID(null);
            if(book.getImageFileName() != null && !book.getImageFileName().isEmpty()) {
                book.setPreSignedURL(fileService.generatePreSignedUrl(book.getImageFileName(), SdkHttpMethod.GET));
            }
        });

        return pr;
    }
}
