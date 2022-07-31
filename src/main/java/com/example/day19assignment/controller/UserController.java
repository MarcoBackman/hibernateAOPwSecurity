package com.example.day19assignment.controller;

import com.example.day19assignment.domain.User;
import com.example.day19assignment.domain.UserDetail;
import com.example.day19assignment.domain.common.ResponseStatus;
import com.example.day19assignment.domain.request.UserCreateRequest;
import com.example.day19assignment.domain.response.AllUserResponse;
import com.example.day19assignment.domain.response.UserResponse;
import com.example.day19assignment.exception.ExistingUserException;
import com.example.day19assignment.exception.UserCreationException;
import com.example.day19assignment.exception.UserNotFoundException;
import com.example.day19assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private int idIndex; // Will be reset on application restart
    private UserService userService;

    @Autowired
    public void setRestTemplate(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public UserResponse createUser(@RequestBody UserCreateRequest request) throws Exception {


        if (request.getUsername().isEmpty()
                || request.getPassword().isEmpty()
                || request.getFirstname().isEmpty()
                || request.getLastname().isEmpty()
                || request.getEmail().isEmpty()) {
            throw new UserCreationException();
        }

        if (userService.findUserByName(request.getUsername()) == null ||
            userService.findUserByEmail(request.getEmail()) == null) {
            throw new ExistingUserException();
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .status(true)
                .build();


        userService.creatUser(newUser);

        UserDetail newUserDetail = UserDetail.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .user(newUser)
                .build();

        newUser.setUserDetail(newUserDetail);
        userService.creatUserDetail(newUserDetail);



        return UserResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("YES")
                                .build())
                .message(newUser.toString())
                .build();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('delete')")
    public void deleteUser(@RequestParam(name="userId") String userId) throws UserNotFoundException {

        User targetUser = userService.findUser(Integer.parseInt(userId));
        if (targetUser == null) {
            throw new UserNotFoundException();
        }

        userService.removeUser(Integer.parseInt(userId));
    }

    @PreAuthorize("hasAuthority('write')")
    @PatchMapping("/{userId}/status")
    public void activeDeactivateUser(@PathVariable Integer userId,
                                   @RequestParam(name="activate") String activate) throws UserNotFoundException {

        User targetUser = userService.findUser(userId);
        if (targetUser == null) {
            throw new UserNotFoundException();
        }

        boolean status = Boolean.parseBoolean(activate);
        userService.changeUserStatus(userId, status);
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping
    public AllUserResponse getAllUser() {
        List<User> userList = userService.findAll();
        List<String> outputList = new ArrayList<>();
        userList.forEach(user -> outputList.add(user.getId().toString() + " : " + user.getUsername()));
        return AllUserResponse.builder().status(
                ResponseStatus.builder()
                        .success(true)
                        .message("Returning all users")
                        .build()
        ).formedList(outputList).build();
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/info/{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) throws UserNotFoundException {
        User targetUser = userService.findUser(userId);

        if (targetUser == null) {
            throw new UserNotFoundException();
        }

        return UserResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("YES")
                                .build())
                .message(targetUser.toString())
                .build();
    }
}
