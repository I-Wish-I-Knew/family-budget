package com.shaygorodskaia.familybudget.service.impl;

import com.shaygorodskaia.familybudget.dto.UserDto;
import com.shaygorodskaia.familybudget.exception.NotFoundException;
import com.shaygorodskaia.familybudget.mapper.UserMapper;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.repository.FamilyRepository;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import com.shaygorodskaia.familybudget.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.shaygorodskaia.familybudget.util.Constants.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final FamilyRepository familyRepository;

    @Override
    public UserDto get(Long id) {
        User user = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(USER_NOT_FOUND, id)));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll(Long familyId) {
        List<User> users = new ArrayList<>();

        if (familyRepository.existsById(familyId)) {
            users = repository.findAllByFamilyId(familyId);
        }
        return convertToUserDtos(users);
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(USER_NOT_FOUND, id)));

        if (!StringUtils.isBlank(userDto.getName())) {
            user.setName(userDto.getName());
        }
        if (!StringUtils.isBlank(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }

        return UserMapper.toUserDto(repository.save(user));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private List<UserDto> convertToUserDtos(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}
