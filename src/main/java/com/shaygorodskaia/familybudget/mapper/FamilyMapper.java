package com.shaygorodskaia.familybudget.mapper;

import com.shaygorodskaia.familybudget.dto.FamilyDto;
import com.shaygorodskaia.familybudget.model.Family;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FamilyMapper {

    public static Family toFamily(FamilyDto familyDto) {
        return new Family(familyDto.getId(), familyDto.getName(), new ArrayList<>());
    }

    public static FamilyDto toFamilyDto(Family family, List<Long> users) {
        return new FamilyDto(family.getId(), family.getName(), users);
    }
}
