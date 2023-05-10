package com.chuks.security.controller;

import com.chuks.security.dto.ApiResponse;
import com.chuks.security.dto.AuthenticationRequest;
import com.chuks.security.dto.AuthenticationResponse;
import com.chuks.security.dto.RegisterRequest;
import com.chuks.security.entity.User;
import com.chuks.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping
    public AuthenticationResponse signUpUser(@RequestBody RegisterRequest registerRequest){

        return userService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest){
        return userService.authenticate(authenticationRequest);

    }

    @GetMapping("/allUsers")
    public List<User> getAllUser(){
        return userService.findAllUser();
    }


    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable("userid") Integer id) {
        try {
            userService.deleteUserById(id);
            return  new ResponseEntity<>(new ApiResponse<>("000","deleted successfully",null),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse<>("999","failed to delete",null),HttpStatus.OK);
        }
    }

}
