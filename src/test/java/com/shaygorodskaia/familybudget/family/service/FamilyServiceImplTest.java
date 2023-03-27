package com.shaygorodskaia.familybudget.family.service;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.mapper.FamilyMapper;
import com.shaygorodskaia.familybudget.model.Family;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.repository.FamilyRepository;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import com.shaygorodskaia.familybudget.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/schema.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FamilyServiceImplTest {

    @Autowired
    private final FamilyRepository repository;
    @Autowired
    private final UserRepository userRepository;
    private final FamilyService service;

    @Test
    void save() {
        FamilyDto familyDto = getFamilyDto();
        FamilyDto savedFamily = service.save(familyDto);
        Optional<Family> fromRepo = repository.findById(savedFamily.getId());

        assertThat(fromRepo).isNotEmpty()
                .contains(FamilyMapper.toFamily(savedFamily));
    }

    @Test
    void update() {
        Family savedFamily = repository.save(getFamily());
        FamilyDto forUpdate = new FamilyDto(savedFamily.getId(), "newName", new ArrayList<>());

        service.update(savedFamily.getId(), forUpdate);
        Optional<Family> fromRepo = repository.findById(savedFamily.getId());

        assertThat(fromRepo).isNotEmpty()
                .get()
                .hasFieldOrPropertyWithValue("name", forUpdate.getName());
    }

    @Test
    void get() {
        Family family = repository.save(getFamily());
        FamilyDto savedFamily = service.get(family.getId());

        assertThat(savedFamily).isEqualTo(FamilyMapper.toFamilyDto(family, new ArrayList<>()));
    }

    @Test
    void delete() {
        Family family = repository.save(getFamily());
        Optional<Family> fromRepo = repository.findById(family.getId());

        assertThat(fromRepo).isNotEmpty();

        service.delete(family.getId());
        Optional<Family> fromRepoAfterDelete = repository.findById(family.getId());

        assertThat(fromRepoAfterDelete).isEmpty();
    }

    @Test
    void addUser() {
        Family family = repository.save(getFamily());
        User user1 = userRepository.save(new User(1L, "user1", "user1@email.com"));
        User user2 = userRepository.save(new User(2L, "user2", "user2@email.com"));

        FamilyDto familyDtoEmptyUsers = service.get(family.getId());

        assertThat(familyDtoEmptyUsers.getUsers()).isEmpty();

        service.addUser(family.getId(), user1.getId());
        FamilyDto familyDto = service.get(family.getId());

        assertThat(familyDto.getUsers())
                .contains(user1.getId())
                .doesNotContain(user2.getId());
    }

    @Test
    void deleteUser() {
        Family family = repository.save(getFamily());
        User user1 = userRepository.save(new User(1L, "user1", "user1@email.com"));
        User user2 = userRepository.save(new User(2L, "user2", "user2@email.com"));

        service.addUser(family.getId(), user1.getId());
        service.addUser(family.getId(), user2.getId());
        FamilyDto familyDto = service.get(family.getId());

        assertThat(familyDto.getUsers())
                .contains(user1.getId())
                .contains(user2.getId());

        service.deleteUser(family.getId(), user1.getId());
        FamilyDto familyDtoAfterDelete = service.get(family.getId());

        assertThat(familyDtoAfterDelete.getUsers())
                .doesNotContain(user1.getId())
                .contains(user2.getId());
    }

    private FamilyDto getFamilyDto() {
        return new FamilyDto(1L, "Ivanov", new ArrayList<>());
    }

    private Family getFamily() {
        return new Family(1L, "Ivanov", new ArrayList<>());
    }
}