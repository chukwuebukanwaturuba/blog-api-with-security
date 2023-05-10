package com.chuks.security.serviceImpl;

import com.chuks.security.dto.AuthenticationRequest;
import com.chuks.security.dto.AuthenticationResponse;
import com.chuks.security.dto.RegisterRequest;
import com.chuks.security.config.JwtService;
import com.chuks.security.repository.UserRepository;
import com.chuks.security.Enum.Role;
import com.chuks.security.entity.User;
import com.chuks.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
     new UsernamePasswordAuthenticationToken(
             request.getEmail(),
             request.getPassword()
     )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public void deleteUserById( Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("No such user with id: "+ id));
        userRepository.delete(user);
    }
}
