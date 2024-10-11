package com.searchoptimization.resourceservice.controllers;


import com.searchoptimization.reposervice.Dto.UserDto;
import com.searchoptimization.resourceservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public static final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    @GetMapping("/hello")
    public String home(){
        return userService.hello();
    }

    @PutMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto){
        String response = userService.signup(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete_account")
    public ResponseEntity<?> deleteAccount(){
        String response = userService.deleteAccount();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
