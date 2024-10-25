package com.technova.usermanagement.service;

import com.technova.usermanagement.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void saveUser(User user);
    void deleteUser(Long id);
    boolean identificationDocumentExists(String identificationDocument, Long excludeUserId);
}
