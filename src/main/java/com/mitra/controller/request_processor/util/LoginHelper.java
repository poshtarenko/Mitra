package com.mitra.controller.request_processor.util;

import com.mitra.controller.SessionAttributes;
import com.mitra.dto.UserDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import com.mitra.validator.Error;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

public final class LoginHelper {
    public static void loginAndUpdateSessionAttrs(String email, String password, UserService userService, HttpServletRequest req) throws ValidationException {
        Optional<UserDto> user = userService.tryLogin(email, password);
        if (!user.isPresent()) {
            throw new ValidationException(Collections.singletonList(
                    Error.of("CREDENTIALS ARE INVALID", "Введені логін та пароль неправильні")));
        }
        req.getSession().setAttribute(SessionAttributes.USER_ID.name(), user.get().getId());
        req.getSession().setAttribute(SessionAttributes.USER_NAME.name(), user.get().getProfile().getName());
    }
}
