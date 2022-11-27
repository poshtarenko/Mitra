package com.mitra.dto;

import com.mitra.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto implements Dto {
    Integer id;
    String email;
    String password;
    Role role;
    ProfileDto profile;
}
