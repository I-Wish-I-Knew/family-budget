package com.shaygorodskaia.familybudget.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name", length = 150, nullable = false)
    private String name;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
}
