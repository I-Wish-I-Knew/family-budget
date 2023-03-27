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

import java.util.ArrayList;
import java.util.List;

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
    public FamilyDto save(FamilyDto familyDto) {
        Family family = repository.save(FamilyMapper.toFamily(familyDto));
        return FamilyMapper.toFamilyDto(family, new ArrayList<>());
    }

    @Transactional
    @Override
    public FamilyDto update(Long id, FamilyDto familyDto) {
        Family family = getFamily(id);
        if (familyDto.getName() != null) {
            family.setName(familyDto.getName());
        }
        return FamilyMapper.toFamilyDto(repository.save(family), getUsers(id));
    }

    @Override
    public FamilyDto get(Long id) {
        Family family = getFamily(id);
        return FamilyMapper.toFamilyDto(family, getUsers(id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public FamilyDto addUser(Long id, Long userId) {
        Family family = getFamily(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id)));
        repository.addUser(family.getId(), user.getId());
        return FamilyMapper.toFamilyDto(family, getUsers(id));
    }

    @Transactional
    @Override
    public void deleteUser(Long id, Long userId) {
        repository.deleteUser(id, userId);
    }

    private List<Long> getUsers(Long id) {
        return userRepository.findAllIdByFamilyId(id);
    }

    private Family getFamily(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(FAMILY_NOT_FOUND, id)));
    }
}
