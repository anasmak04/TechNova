package com.technova.usermanagement.controller;

import com.technova.usermanagement.model.User;
import com.technova.usermanagement.service.UserService;
import com.technova.usermanagement.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.time.LocalDate;
import java.util.Optional;

public class UserController implements Controller {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.endsWith("/users") && method.equals("GET")) {
            return listUsers();
        } else if (path.endsWith("/users/new") && method.equals("GET")) {
            return newUserForm();
        } else if (path.matches("/users/\\d+/edit") && method.equals("GET")) {
            Long id = extractIdFromPath(path);
            return editUserForm(id);
        } else if (path.matches("/users/\\d+/delete") && method.equals("GET")) {
            Long id = extractIdFromPath(path);
            return deleteUser(id);
        } else if (path.endsWith("/users/save") && method.equals("POST")) {
            return handleUserSave(request);
        }

        return new ModelAndView("redirect:/users");
    }

    private ModelAndView handleUserSave(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        ModelAndView modelAndView;
        User user;

        try {
            // Get identification document and check if it exists
            String identificationDocument = request.getParameter("identificationDocument");
            Long userId = idParam != null && !idParam.isEmpty() ? Long.parseLong(idParam) : null;

            if (userService.identificationDocumentExists(identificationDocument, userId)) {
                modelAndView = new ModelAndView(userId != null ? "editUser" : "userForm");
                user = userId != null ? userService.getUserById(userId).orElse(new User()) : new User();
                modelAndView.addObject("user", user);
                modelAndView.addObject("error", "Identification document already exists");
                return modelAndView;
            }

            // Validate expiration date
            String expirationDateStr = request.getParameter("expirationDate");
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                LocalDate expirationDate = LocalDate.parse(expirationDateStr);
                LocalDate registrationDate = LocalDate.now();
                if (expirationDate.isBefore(registrationDate)) {
                    modelAndView = new ModelAndView(userId != null ? "editUser" : "userForm");
                    user = userId != null ? userService.getUserById(userId).orElse(new User()) : new User();
                    updateUserFields(user, request);
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("error", "Expiration date cannot be before registration date");
                    return modelAndView;
                }
            }

            // Process the save/update
            if (userId != null) {
                Optional<User> optionalUser = userService.getUserById(userId);
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                    updateUserFields(user, request);
                } else {
                    return new ModelAndView("redirect:/users");
                }
            } else {
                user = new User();
                updateUserFields(user, request);
            }

            userService.saveUser(user);
            return new ModelAndView("redirect:/users");

        } catch (Exception e) {
            modelAndView = new ModelAndView(idParam != null ? "editUser" : "userForm");
            user = new User();
            updateUserFields(user, request);
            modelAndView.addObject("user", user);
            modelAndView.addObject("error", "An error occurred: " + e.getMessage());
            return modelAndView;
        }
    }

    private void updateUserFields(User user, HttpServletRequest request) {
        user.setLastName(request.getParameter("lastName"));
        user.setFirstName(request.getParameter("firstName"));
        user.setIdentificationDocument(request.getParameter("identificationDocument"));
        user.setNationality(request.getParameter("nationality"));

        String expirationDateStr = request.getParameter("expirationDate");
        if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
            try {
                LocalDate expirationDate = LocalDate.parse(expirationDateStr);
                user.setExpirationDate(expirationDate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format");
            }
        }
    }

    private ModelAndView listUsers() {
        ModelAndView modelAndView = new ModelAndView("userList");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    private ModelAndView newUserForm() {
        ModelAndView modelAndView = new ModelAndView("userForm");
        User user = new User();
        // Set default expiration date to one year from now
        user.setExpirationDate(LocalDate.now().plusYears(1));
        modelAndView.addObject("user", user);
        modelAndView.addObject("minDate", LocalDate.now());
        return modelAndView;
    }

    private ModelAndView editUserForm(Long id) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            modelAndView.addObject("user", user.get());
            modelAndView.addObject("minDate", user.get().getRegistrationDate());
        } else {
            return new ModelAndView("redirect:/users");
        }
        return modelAndView;
    }

    private ModelAndView deleteUser(Long id) {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/users");
    }

    private Long extractIdFromPath(String path) {
        String[] segments = path.split("/");
        return Long.parseLong(segments[segments.length - 2]);
    }
}
