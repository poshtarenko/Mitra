package com.mitra.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CredentialsDto {
    String email;
    String password;
}
