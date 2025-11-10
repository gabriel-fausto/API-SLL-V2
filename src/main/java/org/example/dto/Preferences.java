package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Preferences {
    private List<String> genres;
}
