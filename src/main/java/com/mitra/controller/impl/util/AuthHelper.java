package com.mitra.controller.impl.util;

import com.mitra.dto.CredentialsDto;
import com.mitra.dto.UserDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import com.mitra.validator.Error;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public final class AuthHelper {

    public static void loginAndUpdateSessionAttrs(String email, String password, UserService userService,
                                                  HttpServletRequest req) throws ValidationException {
        UserDto user = userService.find(new CredentialsDto(email, password))
                .orElseThrow(() -> new ValidationException(Collections.singletonList(
                        Error.of(
                                "CREDENTIALS ARE INVALID",
                                "Введені логін та пароль неправильні"
                        )))
                );

        SessionAttrAccessor.setUser(req, user);
    }
}
