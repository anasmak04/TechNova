package com.technova.usermanagement.service;

import com.technova.usermanagement.model.User;
import com.technova.usermanagement.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean identificationDocumentExists(String identificationDocument, Long excludeUserId) {
        Optional<User> existingUser = userRepository.findByIdentificationDocument(identificationDocument);
        return existingUser.isPresent() && !existingUser.get().getId().equals(excludeUserId);
    }
}
