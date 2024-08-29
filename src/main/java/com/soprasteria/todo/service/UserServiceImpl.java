package com.soprasteria.todo.service;

import com.soprasteria.todo.exception.EmailException;
import com.soprasteria.todo.exception.PhoneNumberException;
import com.soprasteria.todo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.soprasteria.todo.entity.UserDao;
import com.soprasteria.todo.mapper.UserMapper;
import com.soprasteria.todo.model.UserDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    public List<UserDTO> getAllUsers() {
        List<UserDao> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        // Validate email
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailException("Email is already in use.");
        }

        // Validate phone numbers
        String phoneNumberRegex = "^[0-9]{10}$";
        for (String phoneNumber : userDTO.getPhoneNumbers()) {
            if (!phoneNumber.matches(phoneNumberRegex)) {
                throw new PhoneNumberException("Phone number " + phoneNumber + " is not in the correct format. It should be 10 digits.");
            }
        }

        // Map to entity and save user
        UserDao userDao = UserMapper.toEntity(userDTO);
        UserDao savedUser = userRepository.save(userDao);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        Optional<UserDao> userDao = userRepository.findById(id);
        return userDao.map(UserMapper::toDTO).orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<UserDao> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserDao userDao = UserMapper.toEntity(userDTO);
            userDao.setId(id);
            UserDao updatedUser = userRepository.save(userDao);
            return UserMapper.toDTO(updatedUser);
        } else {
            return null;
        }
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<UserDTO> getBookmarkedUsers() {
        List<UserDao> bookmarkedUsers = userRepository.findByBookmarkedTrue();
        return bookmarkedUsers.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO toggleBookmark(Long id, Boolean bookmarked) {
        Optional<UserDao> userDao = userRepository.findById(id);
        if (userDao.isPresent()) {
            UserDao user = userDao.get();
            user.setBookmarked(bookmarked);
            UserDao updatedUser = userRepository.save(user);
            return UserMapper.toDTO(updatedUser);
        }
        return null;
    }

    public List<UserDao> searchUsers(String searchTerm) {
        return userRepository.searchByNameOrDateOfBirth(searchTerm);
    }


}

