package com.soprasteria.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "todo_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Size(min = 3, max = 15)
    @Column(nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneDao> phoneNumbers;

    @Column(nullable = false)
    private Boolean bookmarked;
}

