package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.TrackDto;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DeleteTrackController implements PostController {

    private final TrackService trackService;

    public DeleteTrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int trackId = Integer.parseInt(request.getParameter("trackId"));
        List<TrackDto> profileMusic = trackService.getProfileMusic(myId);

        Optional<TrackDto> optionalTrack = profileMusic.stream()
                .filter(track -> track.getId() == trackId)
                .findFirst();

        if (optionalTrack.isPresent()) {
            trackService.remove(trackId);
        }

        response.sendRedirect(GetUrl.MUSIC.getUrl());
    }
}
