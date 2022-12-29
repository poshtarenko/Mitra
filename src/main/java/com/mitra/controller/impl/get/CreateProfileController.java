package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.entity.Gender;
import com.mitra.service.LocationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProfileController implements GetController {

    private final LocationService locationService;

    public CreateProfileController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cities", locationService.getAll());
        request.setAttribute("genders", Gender.values());
        ControllerUtils.forward(request, response, GetUrl.CREATE_PROFILE.getJspFileName());
    }
}
