package org.example.repository;

import org.example.dto.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.userTable = enhancedClient.table("user", TableSchema.fromBean(User.class));
    }

    public User findByEmail(String email) {
        return userTable.getItem(Key.builder().partitionValue(email).build());
    }

    public User save(User user) {
        userTable.putItem(user);
        return user;
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    public User update(User user) {
        userTable.updateItem(user);
        return user;
    }
}