package com.shaygorodskaia.familybudget.user.service;

import com.shaygorodskaia.familybudget.exception.NotFoundException;
import com.shaygorodskaia.familybudget.family.FamilyRepository;
import com.shaygorodskaia.familybudget.user.User;
import com.shaygorodskaia.familybudget.user.UserDto;
import com.shaygorodskaia.familybudget.user.UserMapper;
import com.shaygorodskaia.familybudget.user.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final FamilyRepository familyRepository;

    @Override
    public UserDto get(Long id) {
        User user = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User %d was not found.", id)));
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
                new NotFoundException(String.format("User %d was not found.", id)));

        if (!StringUtils.isBlank(userDto.getName())) {
            user.setName(userDto.getName());
        }
        if (!StringUtils.isBlank(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFamily() != null) {
            user.setFamily(userDto.getFamily());
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
