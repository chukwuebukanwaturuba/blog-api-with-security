package com.chuks.security.services;


import com.chuks.security.dto.AuthenticationRequest;
import com.chuks.security.dto.AuthenticationResponse;
import com.chuks.security.dto.RegisterRequest;
import com.chuks.security.entity.User;

import java.util.List;

public interface UserService {
//
//    User registerUser(UserDTO userDTO);
//    User registerAdmin(UserDTO userDTO);
//    User loginUser(UserDTO userDTO);
//    List<User> findAllUser();
//    void deleteUserById( Long id);
 AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    List<User> findAllUser();
    void deleteUserById( Integer id);

}
