package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PagedResult {
    String lastEvaluatedKey;
    List<Book> books;
    int pageSize;
    long totalSize;
}
