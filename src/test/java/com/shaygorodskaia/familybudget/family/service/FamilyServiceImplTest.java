package com.shaygorodskaia.familybudget.family.service;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.mapper.FamilyMapper;
import com.shaygorodskaia.familybudget.model.Family;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.repository.FamilyRepository;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import com.shaygorodskaia.familybudget.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FamilyServiceImplTest {

    @Autowired
    private final FamilyRepository repository;
    @Autowired
    private final UserRepository userRepository;
    private final FamilyService service;
    private User user1;
    private User user2;

    @BeforeAll
    void setup() {
        user1 = userRepository.save(new User(1L, "user1", "user1@email.com", "111111"));
        user2 = userRepository.save(new User(2L, "user2", "user2@email.com", "11111"));
    }

    @Test
    void save() {
        FamilyDto familyDto = getFamilyDto();
        FamilyDto savedFamily = service.save(familyDto, user1.getId());
        Optional<Family> fromRepo = repository.findById(savedFamily.getId());

        assertThat(fromRepo).isNotEmpty()
                .contains(FamilyMapper.toFamily(savedFamily));
        assertThat(savedFamily.getUsers()).hasSize(1)
                .contains(user1.getId());
    }

    @Test
    void update() {
        Family family = getFamily();
        repository.addUser(family.getId(), user1.getId());
        FamilyDto forUpdate = new FamilyDto(family.getId(), "newName", new ArrayList<>());

        service.update(family.getId(), forUpdate, user1.getId());
        Optional<Family> fromRepo = repository.findById(family.getId());

        assertThat(fromRepo).isNotEmpty()
                .get()
                .hasFieldOrPropertyWithValue("name", forUpdate.getName());
    }

    @Test
    void get() {
        Family family = getFamily();
        FamilyDto familyDto = FamilyMapper.toFamilyDto(family);
        familyDto.getUsers().add(user1.getId());
        repository.addUser(family.getId(), user1.getId());
        FamilyDto savedFamily = service.get(family.getId(), user1.getId());

        assertThat(savedFamily).isEqualTo(familyDto);
    }

    @Test
    void delete() {
        Family family = getFamily();
        repository.addUser(family.getId(), user1.getId());

        Optional<Family> fromRepo = repository.findByIdAndUserId(family.getId(), user1.getId());
        System.out.println(repository.findAll());

        assertThat(fromRepo).isNotEmpty();

        service.delete(family.getId(), user1.getId());

        assertThat(repository.existsById(family.getId())).isFalse();
    }

    @Test
    void addUser() {
        Family family = getFamily();
        repository.addUser(family.getId(), user1.getId());
        FamilyDto familyDtoWithUser1 = service.get(family.getId(), user1.getId());

        assertThat(familyDtoWithUser1.getUsers()).hasSize(1);

        service.addUser(family.getId(), user2.getId(), user1.getId());
        FamilyDto familyDto = service.get(family.getId(), user2.getId());

        assertThat(familyDto.getUsers())
                .contains(user1.getId())
                .contains(user2.getId());
    }

    @Test
    void deleteUser() {
        Family family = repository.save(getFamily());
        repository.addUser(family.getId(), user1.getId());
        repository.addUser(family.getId(), user2.getId());

        FamilyDto familyDto = service.get(family.getId(), user1.getId());

        assertThat(familyDto.getUsers())
                .contains(user1.getId())
                .contains(user2.getId());

        service.deleteUser(family.getId(), user1.getId(), user2.getId());
        FamilyDto familyDtoAfterDelete = service.get(family.getId(), user2.getId());

        assertThat(familyDtoAfterDelete.getUsers()).hasSize(1)
                .contains(user2.getId())
                .doesNotContain(user1.getId());
    }

    private FamilyDto getFamilyDto() {
        return new FamilyDto(1L, "Ivanov", new ArrayList<>());
    }

    private Family getFamily() {
        return repository.save(new Family(1L, "Ivanov", new ArrayList<>()));
    }
}