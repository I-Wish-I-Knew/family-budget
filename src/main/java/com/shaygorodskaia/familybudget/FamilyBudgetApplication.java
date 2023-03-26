package com.shaygorodskaia.familybudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FamilyBudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyBudgetApplication.class, args);
    }

}
