package com.shaygorodskaia.familybudget.service.impl;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.exception.NotFoundException;
import com.shaygorodskaia.familybudget.mapper.FamilyMapper;
import com.shaygorodskaia.familybudget.model.Family;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.repository.FamilyRepository;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import com.shaygorodskaia.familybudget.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.shaygorodskaia.familybudget.util.Constants.FAMILY_NOT_FOUND;
import static com.shaygorodskaia.familybudget.util.Constants.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository repository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public FamilyDto save(FamilyDto familyDto, Long authUserId) {
        Family family = repository.save(FamilyMapper.toFamily(familyDto));
        repository.addUser(family.getId(), authUserId);
        familyDto = FamilyMapper.toFamilyDto(family);
        familyDto.getUsers().addAll(getUsers(family.getId()));
        return familyDto;
    }

    @Transactional
    @Override
    public FamilyDto update(Long id, FamilyDto familyDto, Long authUserId) {
        Family family = getFamily(id, authUserId);
        if (familyDto.getName() != null) {
            family.setName(familyDto.getName());
        }
        family = repository.save(family);
        familyDto = FamilyMapper.toFamilyDto(family);
        familyDto.getUsers().addAll(getUsers(id));
        return familyDto;
    }

    @Override
    public FamilyDto get(Long id, Long authUserId) {
        Family family = getFamily(id, authUserId);
        FamilyDto familyDto = FamilyMapper.toFamilyDto(family);
        familyDto.getUsers().addAll(getUsers(id));
        return familyDto;
    }

    @Transactional
    @Override
    public void delete(Long id, Long authUserId) {
        repository.deleteByIdAndUserId(id, authUserId);
    }

    @Transactional
    @Override
    public FamilyDto addUser(Long id, Long userId, Long authUserId) {
        Family family = getFamily(id, authUserId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id)));
        repository.addUser(family.getId(), user.getId());
        FamilyDto familyDto = FamilyMapper.toFamilyDto(family);
        familyDto.getUsers().addAll(getUsers(id));
        return familyDto;
    }

    @Transactional
    @Override
    public void deleteUser(Long id, Long userId, Long authUserId) {
        repository.deleteUser(id, userId, authUserId);
    }

    private Collection<Long> getUsers(Long id) {
        return repository.findAllUsersInFamily(id);
    }

    private Family getFamily(Long id, Long authUserId) {
        return repository.findByIdAndUserId(id, authUserId)
                .orElseThrow(() -> new NotFoundException(String.format(FAMILY_NOT_FOUND, id)));
    }
}
