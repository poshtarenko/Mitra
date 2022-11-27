package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.TrackDto;
import com.mitra.dto.UserDto;
import com.mitra.service.ServiceFactory;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public class MyMusicProcessor extends AbstractRequestProcessor {

    private final TrackService trackService = ServiceFactory.getInstance().getTrackService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int profileId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();
        List<TrackDto> profileMusic = trackService.getProfileMusic(profileId);
        request.setAttribute("tracks", profileMusic);
        forward(request, response, UrlPath.MUSIC.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            Part audio = request.getPart("audio");

            int profileId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();

            TrackDto trackDto = TrackDto.builder()
                    .name(name)
                    .author(author)
                    .ownerId(profileId)
                    .build();

            trackService.save(trackDto, audio.getInputStream());
        } else if (action.equals("delete")) {
            int trackId = Integer.parseInt(request.getParameter("trackId"));
            trackService.remove(trackId);
        }

        redirect(response, UrlPath.MUSIC.get());
    }
}
