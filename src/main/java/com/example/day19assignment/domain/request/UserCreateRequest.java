package com.example.day19assignment.domain.request;

import com.example.day19assignment.domain.UserDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}
