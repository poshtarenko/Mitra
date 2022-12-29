package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.TrackDto;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class AddTrackController implements PostController {

    private final TrackService trackService;

    public AddTrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("ADD")) {
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            Part audio = request.getPart("audio");

            int profileId = SessionAttrAccessor.getProfileId(request);

            TrackDto trackDto = TrackDto.builder()
                    .name(name)
                    .author(author)
                    .ownerId(profileId)
                    .build();

            trackService.save(trackDto, audio.getInputStream());
        } else if (action.equals("DELETE")) {
            int trackId = Integer.parseInt(request.getParameter("trackId"));
            trackService.remove(trackId);
        } else if (action.equals("SET_PREVIEW")) {
            int trackId = Integer.parseInt(request.getParameter("trackId"));
            int profileId = SessionAttrAccessor.getProfileId(request);
            trackService.setProfilePreviewTrack(profileId, trackId);
        }

        response.sendRedirect(GetUrl.MUSIC.getUrl());
    }
}
