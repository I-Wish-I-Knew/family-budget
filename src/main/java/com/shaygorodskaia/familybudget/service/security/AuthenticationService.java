package com.shaygorodskaia.familybudget.service.security;

import com.shaygorodskaia.familybudget.exception.NotFoundException;
import com.shaygorodskaia.familybudget.model.User;
import com.shaygorodskaia.familybudget.model.security.AuthenticationRequest;
import com.shaygorodskaia.familybudget.model.security.AuthenticationResponse;
import com.shaygorodskaia.familybudget.model.security.RegisterRequest;
import com.shaygorodskaia.familybudget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.shaygorodskaia.familybudget.util.Constants.USER_NOT_FOUND_BY_EMAIL;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL, request.getEmail())));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
