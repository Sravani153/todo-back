package com.soprasteria.todo.mapper;

import com.soprasteria.todo.entity.PhoneDao;
import com.soprasteria.todo.entity.UserDao;
import com.soprasteria.todo.model.UserDTO;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(UserDao userDao) {
        if (userDao == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDao.getId());
        userDTO.setName(userDao.getName());
        userDTO.setDateOfBirth(userDao.getDateOfBirth().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        userDTO.setGender(userDao.getGender());
        userDTO.setEmail(userDao.getEmail());
        userDTO.setPhoneNumbers(userDao.getPhoneNumbers().stream()
                .map(phoneDao -> phoneDao.getNumber())
                .collect(Collectors.toList()));
        userDTO.setBookmarked(userDao.getBookmarked());

        return userDTO;
    }

    public static UserDao toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserDao userDao = new UserDao();
        userDao.setId(userDTO.getId());
        userDao.setName(userDTO.getName());
        userDao.setDateOfBirth(java.util.Date.from(userDTO.getDateOfBirth().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        userDao.setGender(userDTO.getGender());
        userDao.setEmail(userDTO.getEmail());
        userDao.setPhoneNumbers(userDTO.getPhoneNumbers().stream()
                .map(phoneNumber -> {
                    PhoneDao phoneDao = new PhoneDao();
                    phoneDao.setNumber(phoneNumber);
                    phoneDao.setUser(userDao);
                    return phoneDao;
                })
                .collect(Collectors.toList()));
        userDao.setBookmarked(userDTO.getBookmarked());

        return userDao;
    }
}
