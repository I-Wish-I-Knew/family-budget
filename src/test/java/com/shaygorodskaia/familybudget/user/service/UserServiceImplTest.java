package com.shaygorodskaia.familybudget.user.service;

import com.shaygorodskaia.familybudget.dto.UserDto;
import com.shaygorodskaia.familybudget.mapper.UserMapper;
import com.shaygorodskaia.familybudget.model.Family;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.repository.FamilyRepository;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import com.shaygorodskaia.familybudget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final FamilyRepository familyRepository;
    private final UserService service;

    @Test
    void get() {
        User user = repository.save(createUser(1L));
        UserDto userDto = service.get(user.getId());

        assertThat(userDto).isEqualTo(UserMapper.toUserDto(user));
    }

    @Test
    void getAll() {
        User user1 = repository.save(createUser(1L));
        User user2 = repository.save(createUser(2L));
        User user3 = repository.save(createUser(3L));
        Family family = familyRepository.save(new Family(1L, "family", new ArrayList<>()));

        familyRepository.addUser(family.getId(), user1.getId());
        familyRepository.addUser(family.getId(), user2.getId());

        List<UserDto> users = service.getAll(family.getId());

        assertThat(users).hasSize(2)
                .contains(UserMapper.toUserDto(user1))
                .contains(UserMapper.toUserDto(user2))
                .doesNotContain(UserMapper.toUserDto(user3));
    }

    @Test
    void save() {
        UserDto userDto = UserMapper.toUserDto(createUser(1L));
        UserDto savedUser = service.save(userDto);
        Optional<User> fromRepo = repository.findById(savedUser.getId());

        assertThat(fromRepo).isNotEmpty()
                .contains(UserMapper.toUser(savedUser));
    }

    @Test
    void update() {
        User user = repository.save(createUser(1L));
        User userForUpdate = User.builder()
                .id(user.getId())
                .name("updatedUser")
                .build();

        service.update(user.getId(), UserMapper.toUserDto(userForUpdate));

        Optional<User> updatedUser = repository.findById(user.getId());

        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get())
                .hasFieldOrPropertyWithValue("name", userForUpdate.getName())
                .hasFieldOrPropertyWithValue("email", user.getEmail());
    }

    @Test
    void delete() {
        User user = repository.save(createUser(1L));
        Optional<User> fromRepo = repository.findById(user.getId());

        assertThat(fromRepo).isNotEmpty();

        service.delete(fromRepo.get().getId());
        Optional<User> deletedUser = repository.findById(user.getId());

        assertThat(deletedUser).isEmpty();
    }

    private User createUser(Long id) {
        return User.builder()
                .name("user" + id)
                .email("user" + id + "@email.com")
                .password("111111")
                .build();
    }
}