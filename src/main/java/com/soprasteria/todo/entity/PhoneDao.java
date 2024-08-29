package com.soprasteria.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhoneDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "number", columnDefinition = "varchar")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_FK", nullable = false)
    private UserDao user;
}


