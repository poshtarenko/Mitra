package com.mitra.controller.request_processor.util;

import com.mitra.controller.SessionAttributes;
import com.mitra.dto.UserDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class LoginHelper {
    public static void login(String email, String password, UserService userService, HttpServletRequest req){
        Optional<UserDto> user = userService.tryLogin(email, password);
        if (!user.isPresent()) {
            throw new ValidationException("Credentials are invalid");
        }
        req.getSession().setAttribute(SessionAttributes.USER_ID.name(), user.get().getId());
        req.getSession().setAttribute(SessionAttributes.USER_NAME.name(), user.get().getProfile().getName());
    }
}
