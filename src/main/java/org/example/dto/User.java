package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Date;

@Data
@NoArgsConstructor
@DynamoDbBean
public class User {
    private String userId;
    private String passwordHash;
    private String email;
    private String name;
    private String cpf;
    private Date birthDate;
    private String phoneNumber;
    private String cep;
    private String state;
    private String city;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("user_id")
    public String getUserId() { return userId; }

    @DynamoDbAttribute("email")
    public String getEmail() { return email; }
}