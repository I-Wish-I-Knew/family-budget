package com.shaygorodskaia.familybudget.controller;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.service.FamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/families")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService service;

    @GetMapping("/{familyId}")
    public FamilyDto get(@PathVariable Long familyId) {
        return service.get(familyId);
    }

    @PostMapping
    public FamilyDto save(@RequestBody @Valid FamilyDto familyDto) {
        return service.save(familyDto);
    }

    @PatchMapping("/{familyId}")
    public FamilyDto update(@PathVariable Long familyId,
                            @RequestBody @Valid FamilyDto familyDto) {
        return service.update(familyId, familyDto);
    }

    @DeleteMapping("/{familyId}")
    public void delete(@PathVariable Long familyId) {
        service.delete(familyId);
    }

    @PutMapping("/{familyId}/{userId}")
    public FamilyDto addUser(@PathVariable Long familyId,
                             @PathVariable Long userId) {
        return service.addUser(familyId, userId);
    }

    @DeleteMapping("/{familyId}/{userId}")
    public void deleteUser(@PathVariable Long familyId,
                           @PathVariable Long userId) {
        service.deleteUser(familyId, userId);
    }
}
