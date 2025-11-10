package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Address {
    private String cep;
    private String state;
    private String city;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
}