package com.gb.um.controller;

import com.gb.um.model.request.UserRegistrationRequest;
import com.gb.um.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * register User to access different api in application
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Registration Api")
public class UserManagementRestController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserManagementRestController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * Add new user which then can access the other api's in grocery store application
     * @param userRegistrationRequest the user details to create application user
     * @return status of user creation
     */
    @PostMapping("/register")
    @Operation(description = "Register new user to user management.")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        userInfoService.registerUser(userRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
