package org.example.mybookmarks.services;

import org.example.mybookmarks.datamodel.UserDAO;
import org.example.mybookmarks.datamodel.UsersRepository;
import org.example.mybookmarks.security.MyBookmarksUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public JwtUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO userDAO = usersRepository.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new MyBookmarksUserDetails(userDAO.getEmail(), userDAO.getPasswordHash(), userDAO.getId());
    }
}