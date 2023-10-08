package org.example.mybookmarks.security;

import java.util.List;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class MyBookmarksUserDetails extends User {
    private final Long userId;

    public MyBookmarksUserDetails(String username, String password, Long userId) {
        super(username, password, List.of());
        this.userId = userId;
    }
}
