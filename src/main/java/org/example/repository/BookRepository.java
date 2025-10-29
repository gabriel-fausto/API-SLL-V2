package org.example.repository;

import org.example.dto.Book;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BookRepository {

    private final DynamoDbTable<Book> bookTable;

    public BookRepository(DynamoDbEnhancedClient enhancedClient) {
        this.bookTable = enhancedClient.table("book", TableSchema.fromBean(Book.class));
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        bookTable.scan().items().forEach(books::add);
        return books;
    }

    public Book findById(String id) {
        return bookTable.getItem(Key.builder().partitionValue(id).build());
    }

    public Book save(Book book) {
        book.setId(UUID.randomUUID().toString());
        bookTable.putItem(book);
        return book;
    }

    public Book update(Book book) {
        bookTable.updateItem(book);
        return book;
    }

    public void deleteById(String id) {
        bookTable.deleteItem(Key.builder().partitionValue(id).build());
    }
}
