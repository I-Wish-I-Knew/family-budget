package com.shaygorodskaia.familybudget.user.service;

import com.shaygorodskaia.familybudget.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto get(Long id);

    List<UserDto> getAll(Long familyId);

    UserDto save(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);
}
