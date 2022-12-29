package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTrackController implements PostController {

    private final TrackService trackService;

    public DeleteTrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo : check user is owner of song

        int trackId = Integer.parseInt(request.getParameter("trackId"));
        trackService.remove(trackId);

        response.sendRedirect(GetUrl.MUSIC.getUrl());
    }
}
