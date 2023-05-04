package com.shaygorodskaia.familybudget.mapper;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.model.Family;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class FamilyMapper {

    public static Family toFamily(FamilyDto familyDto) {
        return new Family(familyDto.getId(), familyDto.getName(), new ArrayList<>());
    }

    public static FamilyDto toFamilyDto(Family family) {
        return new FamilyDto(family.getId(), family.getName(), new ArrayList<>());
    }
}
