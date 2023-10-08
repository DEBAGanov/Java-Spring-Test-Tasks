package org.example.mybookmarks.datamodel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends CrudRepository<Bookmark, Long> {

    List<Bookmark> findAllByUser(UserDAO user);

    List<Bookmark> findAllByUserAndTag(UserDAO user, String tag);

    Optional<Bookmark> findByIdAndUserId(Long userId, Long bookmarkId);

    void deleteBookmarkById(Long bookmarkId);
}
