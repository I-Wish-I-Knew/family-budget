package com.shaygorodskaia.familybudget.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .family(userDto.getFamily())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .family(user.getFamily())
                .build();
    }
}
