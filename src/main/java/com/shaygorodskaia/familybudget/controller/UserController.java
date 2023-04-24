package com.shaygorodskaia.familybudget.controller;

import com.shaygorodskaia.familybudget.dto.UserDto;
import com.shaygorodskaia.familybudget.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService service;

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable Long userId) {
        return service.get(userId);
    }

    @GetMapping
    public List<UserDto> getAll(@RequestParam Long familyId) {
        return service.getAll(familyId);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId,
                          @RequestBody @Valid UserDto userDto) {
        return service.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        service.delete(userId);
    }
}
