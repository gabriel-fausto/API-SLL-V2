package org.example.repository;

import org.example.Util.DynamoDbAttributeValueSerializer;
import org.example.dto.Book;
import org.example.dto.PagedResult;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

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

    public PagedResult findBooksPage(String exclusiveStartKey, int pageSize) {
        ScanEnhancedRequest.Builder requestBuilder = ScanEnhancedRequest.builder().limit(pageSize);
        if (exclusiveStartKey != null) {
            requestBuilder.exclusiveStartKey(DynamoDbAttributeValueSerializer.decodeToExclusiveStartKey(exclusiveStartKey));
        }

        Page<Book> page = bookTable.scan(requestBuilder.build()).iterator().next();

        PagedResult result = new PagedResult();
        result.setBooks(new ArrayList<>());
        page.items().forEach(result.getBooks()::add);
        result.setLastEvaluatedKey(DynamoDbAttributeValueSerializer.encodeLastEvaluatedKey(page.lastEvaluatedKey()));
        result.setPageSize(pageSize);
        result.setTotalSize(countBooks());
        return result;
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

    public long countBooks() {
        return bookTable.scan().items().stream().count();
    }

    public void deleteById(String id) {
        bookTable.deleteItem(Key.builder().partitionValue(id).build());
    }
}
