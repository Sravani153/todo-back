package com.soprasteria.todo.repository;

import com.soprasteria.todo.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserDao, Long> {
    boolean existsByEmail(String email);
    List<UserDao> findByBookmarkedTrue();
    @Query("SELECT u FROM UserDao u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(FUNCTION('TO_CHAR', u.dateOfBirth, 'Month DD, YYYY')) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<UserDao> searchByNameOrDateOfBirth(@Param("searchTerm") String searchTerm);
}
