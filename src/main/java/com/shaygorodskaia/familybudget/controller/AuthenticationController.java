package com.shaygorodskaia.familybudget.controller;

import com.shaygorodskaia.familybudget.model.security.AuthenticationRequest;
import com.shaygorodskaia.familybudget.model.security.AuthenticationResponse;
import com.shaygorodskaia.familybudget.model.security.RegisterRequest;
import com.shaygorodskaia.familybudget.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
