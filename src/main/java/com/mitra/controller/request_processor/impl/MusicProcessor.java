package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.dto.TrackDto;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public class MusicProcessor extends AbstractRequestProcessor {

    private final TrackService trackService;

    public MusicProcessor(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        List<TrackDto> profileMusic = trackService.getProfileMusic(myId);
        request.setAttribute("tracks", profileMusic);
        forward(request, response, AppUrl.MUSIC.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("ADD")) {
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            Part audio = request.getPart("audio");

            int profileId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

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
            int profileId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
            trackService.setProfilePreviewTrack(profileId, trackId);
        }

        redirect(response, AppUrl.MUSIC.getUrl());
    }
}
