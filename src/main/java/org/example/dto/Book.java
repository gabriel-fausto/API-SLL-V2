package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
@NoArgsConstructor
public class Book {
    private String id;

    @NotBlank(message = "Título não pode ser nulo ou vazio.")
    private String title;

    @NotBlank(message = "Autor não pode ser nulo ou vazio.")
    private String author;

    @NotBlank(message = "Categoria não pode ser nulo ou vazio.")
    private String category;

    @NotBlank(message = "Condição não pode ser nulo ou vazio.")
    private String condition;

    @NotBlank(message = "Tipo não pode ser nulo ou vazio.")
    private String type;

    private String description;

    private String imageFileName;

    private transient String preSignedURL;

    private String userID;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "book_id")
    public String getId() {
        return id;
    }
}