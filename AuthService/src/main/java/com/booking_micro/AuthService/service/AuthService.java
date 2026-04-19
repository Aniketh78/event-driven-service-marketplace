package com.booking_micro.AuthService.service;


import com.booking_micro.AuthService.config.JwtUtil;
import com.booking_micro.AuthService.constants.Role_constants;
import com.booking_micro.AuthService.dto.LoginRequest;
import com.booking_micro.AuthService.dto.RegisterRequest;
import com.booking_micro.AuthService.entity.User;
import com.booking_micro.AuthService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(RegisterRequest registerRequest) throws Exception {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new Exception("User with this email already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role_constants.USER);

        userRepository.save(user);

        return "User Registered Successfully";

    }

    public String registerService(RegisterRequest registerRequest) throws Exception {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new Exception("User with this email already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role_constants.SUPPLIER);

        userRepository.save(user);

        return "Service Provider Registered Successfully";

    }

    public String login(LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found with this email"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new Exception("Invalid password");
        }

        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
    }
}
