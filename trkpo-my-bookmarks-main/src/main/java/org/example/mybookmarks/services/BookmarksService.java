package org.example.mybookmarks.services;

import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.example.mybookmarks.datamodel.Bookmark;
import org.example.mybookmarks.datamodel.UserDAO;
import org.example.mybookmarks.model.BadInputParameters;
import org.example.mybookmarks.model.BookmarkDTO;
import org.example.mybookmarks.datamodel.BookmarksRepository;
import org.example.mybookmarks.model.BookmarksSortingOrder;
import org.example.mybookmarks.model.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookmarksService {
    private final BookmarksRepository repo;
    private final ModelMapper mapper;

    private final UrlTitleFetcher urlTitleFetcher;

    public BookmarksService(BookmarksRepository repo, ModelMapper mapper, UrlTitleFetcher urlTitleFetcher) {
        this.repo = repo;
        this.mapper = mapper;
        this.urlTitleFetcher = urlTitleFetcher;
    }

    @Transactional
    public void deleteUserBookmark(Long userId, Long bookmarkId) {
        repo.deleteById(bookmarkId);
    }

    public BookmarkDTO findUserBookmark(Long userId, Long bookmarkId) {
        Bookmark bookmark = repo.findByIdAndUserId(userId, bookmarkId)
                .orElseThrow(() -> new ResourceNotFoundException("bookmark " + bookmarkId));

        return mapper.map(bookmark, BookmarkDTO.class);
    }

    public List<BookmarkDTO> findUserBookmarks(Long userId, String tag, String sortingOrder) {
        UserDAO userDAO = new UserDAO(userId);

        List<Bookmark> found;
        if (tag == null) {
            found = repo.findAllByUser(userDAO);
        } else if (tag.length() == 0) {
            found = repo.findAllByUserAndTag(userDAO, null);
        } else {
            found = repo.findAllByUserAndTag(userDAO, tag);
        }

        return found.stream()
                .map(e -> mapper.map(e, BookmarkDTO.class))
                .sorted(comparatorFromString(sortingOrder))
                .toList();
    }

    private Comparator<? super BookmarkDTO> comparatorFromString(String sortingOrder) {
        if (sortingOrder == null) {
            return Comparator.comparing(BookmarkDTO::getId);
        }
        try {
            BookmarksSortingOrder order = BookmarksSortingOrder.valueOf(sortingOrder);
            return switch (order) {
                case BY_URL -> Comparator.comparing(BookmarkDTO::getUrl);
                case BY_TITLE -> Comparator.comparing(BookmarkDTO::getTitle);
                case BY_DATE -> Comparator.comparing(BookmarkDTO::getCreatedDate);
            };
        } catch (Exception e) {
            throw new BadInputParameters("invalid sorting order" + sortingOrder);
        }
    }

    public BookmarkDTO addUserBookmark(Long userId, String url, String tag) {
        Bookmark bookmark = new Bookmark(url, tag, urlTitleFetcher.fetchAndGetTitle(url), new UserDAO(userId));
        repo.save(bookmark);
        return mapper.map(bookmark, BookmarkDTO.class);
    }
}
