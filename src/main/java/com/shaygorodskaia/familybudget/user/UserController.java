package com.shaygorodskaia.familybudget.user;

import com.shaygorodskaia.familybudget.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public UserDto save(@RequestBody @Valid UserDto userDto) {
        log.info("Save user {}", userDto);
        return service.save(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable Long userId) {
        log.info("Get user by id {}", userId);
        return service.get(userId);
    }

    @GetMapping
    public List<UserDto> getAll(@RequestParam Long familyId) {
        log.info("Get all users by family id {}", familyId);
        return service.getAll(familyId);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId,
                          @RequestBody UserDto userDto) {
        log.info("Update user {}", userDto);
        return service.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        service.delete(userId);
    }
}
