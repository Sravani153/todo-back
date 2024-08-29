package com.soprasteria.todo.controller;

import com.soprasteria.todo.api.UsersApi;
import com.soprasteria.todo.mapper.UserMapper;
import com.soprasteria.todo.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprasteria.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UsersApi {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/bookmarked")
    public ResponseEntity<List<UserDTO>> getBookmarkedUsers() {
        List<UserDTO> users = userService.getBookmarkedUsers();
        return ResponseEntity.ok(users);
    }
    @PutMapping("/{id}/toggleBookmark")
    public ResponseEntity<UserDTO> toggleBookmark(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        Boolean bookmarked = request.get("bookmarked");
        UserDTO updatedUser = userService.toggleBookmark(id, bookmarked);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam String searchTerm) {
        return userService.searchUsers(searchTerm).stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }


}

