package com.api.bulletinboard.user.controller;

import com.api.bulletinboard.user.dto.User;
import com.api.bulletinboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

   private final UserService userService;


    @PostMapping
     public ResponseEntity<User> signUp(
            @Valid @RequestBody User user
    ) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}