package com.example.day19assignment.domain.response;

import com.example.day19assignment.domain.User;
import com.example.day19assignment.domain.common.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticateResponse {
    private ResponseStatus status;
    private String accessToken;
}