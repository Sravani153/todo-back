package com.soprasteria.todo.service;

import com.soprasteria.todo.entity.UserDao;
import com.soprasteria.todo.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    boolean deleteUser(Long id);
    List<UserDTO> getBookmarkedUsers();
    UserDTO toggleBookmark(Long id, Boolean bookmarked);
    List<UserDao> searchUsers(String searchTerm);
}
