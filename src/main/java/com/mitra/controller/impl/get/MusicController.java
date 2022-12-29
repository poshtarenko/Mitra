package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.TrackDto;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MusicController implements GetController {

    private final TrackService trackService;

    public MusicController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        List<TrackDto> profileMusic = trackService.getProfileMusic(myId);
        request.setAttribute("tracks", profileMusic);
        ControllerUtils.forward(request, response, GetUrl.MUSIC.getJspFileName());
    }
}
