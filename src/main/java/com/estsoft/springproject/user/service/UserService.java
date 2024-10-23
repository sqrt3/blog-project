package com.estsoft.springproject.user.service;

import com.estsoft.springproject.user.domain.Users;
import com.estsoft.springproject.user.domain.dto.AddUserRequest;
import com.estsoft.springproject.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Users save(AddUserRequest dto) {
        /*
        String email = dto.getEmail();
        String password = dto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        Users users = new Users(email, encodedPassword);
        return userRepository.save(users);
         */

        return userRepository.save(
            new Users(dto.getEmail(),
            bCryptPasswordEncoder.encode(dto.getPassword()))
        );
    }
}
