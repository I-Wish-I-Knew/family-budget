package com.shaygorodskaia.familybudget.controller;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.service.FamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/families")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService service;

    @GetMapping("/{familyId}")
    public FamilyDto get(@PathVariable Long familyId,
                         @AuthenticationPrincipal User user) {
        return service.get(familyId, user.getId());
    }

    @PostMapping
    public FamilyDto save(@RequestBody @Valid FamilyDto familyDto,
                          @AuthenticationPrincipal User user) {
        return service.save(familyDto, user.getId());
    }

    @PatchMapping("/{familyId}")
    public FamilyDto update(@PathVariable Long familyId,
                            @RequestBody @Valid FamilyDto familyDto,
                            @AuthenticationPrincipal User user) {
        return service.update(familyId, familyDto, user.getId());
    }

    @DeleteMapping("/{familyId}")
    public void delete(@PathVariable Long familyId,
                       @AuthenticationPrincipal User user) {
        service.delete(familyId, user.getId());
    }

    @PutMapping("/{familyId}/{userId}")
    public FamilyDto addUser(@PathVariable Long familyId,
                             @PathVariable Long userId,
                             @AuthenticationPrincipal User user) {
        return service.addUser(familyId, userId, user.getId());
    }

    @DeleteMapping("/{familyId}/{userId}")
    public void deleteUser(@PathVariable Long familyId,
                           @PathVariable Long userId,
                           @AuthenticationPrincipal User user) {
        service.deleteUser(familyId, userId, user.getId());
    }
}
