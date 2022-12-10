package com.mitra.controller.request_processor.util;

import com.mitra.controller.SessionAttributes;
import com.mitra.dto.UserDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import com.mitra.validator.Error;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public final class LoginHelper {

    public static void loginAndUpdateSessionAttrs(String email, String password, UserService userService,
                                                  HttpServletRequest req) throws ValidationException {
        UserDto user = userService.find(email, password)
                .orElseThrow(() -> new ValidationException(Collections.singletonList(
                        Error.of(
                                "CREDENTIALS ARE INVALID",
                                "Введені логін та пароль неправильні"
                        )))
                );

        req.getSession().setAttribute(SessionAttributes.USER_ID.name(), user.getId());

        String userName = null;
        if (user.getProfile() != null) {
            userName = user.getProfile().getName();
        }

        req.getSession().setAttribute(SessionAttributes.USER_NAME.name(), userName);
    }
}
