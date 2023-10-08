package org.example.mybookmarks.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class UserDAO {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    public UserDAO(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UserDAO(Long id) {
        this.id = id;
    }
}
