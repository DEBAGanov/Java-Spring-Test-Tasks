package org.example.mybookmarks.datamodel;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Data
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;
    private String url;

    private String tag;

    private String title;

    private Instant createdDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserDAO user;

    public Bookmark(String url, String tag, String title, UserDAO user) {
        this.url = url;
        this.tag = tag;
        this.user = user;
        this.title = title;
        this.createdDate = Instant.now();
    }
}
