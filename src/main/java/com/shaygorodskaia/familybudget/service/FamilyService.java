package com.shaygorodskaia.familybudget.service;

import com.shaygorodskaia.familybudget.dto.FamilyDto;

public interface FamilyService {

    FamilyDto save(FamilyDto familyDto, Long authUserId);

    FamilyDto update(Long id, FamilyDto familyDto, Long authUserId);

    FamilyDto get(Long id, Long authUserId);

    void delete(Long id, Long authUserId);

    FamilyDto addUser(Long id, Long userId, Long authUserId);

    void deleteUser(Long id, Long userId, Long authUserId);

}
