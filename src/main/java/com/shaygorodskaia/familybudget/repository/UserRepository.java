package com.shaygorodskaia.familybudget.repository;

import com.shaygorodskaia.familybudget.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.* from users u " +
            "right join family_members fm on u.user_id = fm.user_id", nativeQuery = true)
    List<User> findAllByFamilyId(Long familyId);

    @Query(value = "select u.user_id from users u " +
            "right join family_members fm on u.user_id = fm.user_id", nativeQuery = true)
    List<Long> findAllIdByFamilyId(Long familyId);

    Optional<User> findByEmail(String email);
}
