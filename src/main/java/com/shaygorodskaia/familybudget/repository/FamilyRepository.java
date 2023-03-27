package com.shaygorodskaia.familybudget.repository;

import com.shaygorodskaia.familybudget.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Modifying
    @Query(value = "insert into family_members (family_id, user_id) " +
            "values (:familyId, :userId)", nativeQuery = true)
    void addUser(@Param("familyId") Long familyId,
                 @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from family_members " +
            "where family_id = :familyId and user_id = :userId", nativeQuery = true)
    void deleteUser(@Param("familyId") Long familyId,
                    @Param("userId") Long userId);
}
