package com.example.day19assignment.domain.response;

import com.example.day19assignment.domain.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllUserResponse {
    private ResponseStatus status;
    private List<String> formedList;
}
