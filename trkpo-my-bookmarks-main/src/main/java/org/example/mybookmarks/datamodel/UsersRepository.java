package org.example.mybookmarks.datamodel;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserDAO, Long> {
    Optional<UserDAO> findFirstByEmail(String email);
}
