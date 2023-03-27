package com.shaygorodskaia.familybudget.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
public class FamilyDto {
    private Long id;
    @NotBlank
    @Length(max = 200)
    private String name;
    private List<Long> users;
}
