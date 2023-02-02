package com.shaygorodskaia.familybudget.user;

import com.shaygorodskaia.familybudget.family.Family;
import com.shaygorodskaia.familybudget.util.Create;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    @Length(max = 150)
    private String name;
    @NotBlank(groups = {Create.class})
    @Length(max = 150)
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private Family family;
}
