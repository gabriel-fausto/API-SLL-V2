package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@DynamoDbBean
public class User {
    private String passwordHash;
    private String email;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String phoneNumber;
    private Address address;
    private Preferences preferences;
    private List<String> bookIDs;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("email")
    public String getEmail() { return email; }
}