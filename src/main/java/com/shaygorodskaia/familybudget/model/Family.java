package com.shaygorodskaia.familybudget.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "FAMILIES")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Long id;
    @Column(name = "family_name", length = 200, nullable = false)
    private String name;
    @Transient
    private Collection<Long> users;
}
