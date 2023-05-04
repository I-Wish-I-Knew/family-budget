package com.shaygorodskaia.familybudget.repository;

import com.shaygorodskaia.familybudget.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Modifying
    @Query(value = "insert into family_members (family_id, user_id) " +
            "values (:familyId, :userId)", nativeQuery = true)
    void addUser(@Param("familyId") Long familyId,
                 @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from family_members " +
            "where user_id = :userId " +
            "and family_id in ( " +
            "select fm.family_id from family_members fm " +
            "where fm.family_id = :familyId " +
            "and fm.user_id = :authUserId)", nativeQuery = true)
    void deleteUser(@Param("familyId") Long familyId,
                    @Param("userId") Long userId,
                    @Param("authUserId") Long authUserId);

    @Query(value = "select user_id from family_members fm " +
            "where fm.family_id = :familyId", nativeQuery = true)
    Collection<Long> findAllUsersInFamily(Long familyId);

    @Query(value = "select f.* from families f " +
            "left join family_members fm on f.family_id = fm.family_id " +
            "where f.family_id = :familyId " +
            "and fm.user_id = :userId", nativeQuery = true)
    Optional<Family> findByIdAndUserId(Long familyId,
                                       Long userId);

    @Modifying
    @Query(value = "delete from families f " +
            "where f.family_id IN (" +
            "select fm.family_id from family_members fm " +
            "where fm.family_id = :familyId " +
            "and fm.user_id = :userId)", nativeQuery = true)
    void deleteByIdAndUserId(Long familyId,
                             Long userId);
}
