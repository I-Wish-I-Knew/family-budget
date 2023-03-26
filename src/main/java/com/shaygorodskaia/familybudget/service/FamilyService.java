package com.shaygorodskaia.familybudget.service;

import com.shaygorodskaia.familybudget.dto.FamilyDto;

public interface FamilyService {

    FamilyDto save(FamilyDto familyDto);

    FamilyDto update(Long id, FamilyDto familyDto);

    FamilyDto get(Long id);

    void delete(Long id);

    FamilyDto addUser(Long id, Long userId);

    void deleteUser(Long id, Long userId);

}
