package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ProfileDto;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.LikeService;
import com.mitra.service.ProfileService;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MyProfileController implements GetController {

    private final ProfileService profileService;
    private final TrackService trackService;
    private final LikeService likeService;

    public MyProfileController(ProfileService profileService, TrackService trackService, LikeService likeService) {
        this.profileService = profileService;
        this.trackService = trackService;
        this.likeService = likeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        ProfileDto profile = profileService.find(myId)
                .orElseThrow(() -> new NothingFoundException("Profile not found"));

        List<ProfileDto> friends = likeService.extractMutual(myId, likeService.getProfileLikes(myId)).stream()
                .map(like -> {
                    if (like.getSender().getId() != myId)
                        return like.getSender();
                    else if (like.getReceiver().getId() != myId)
                        return like.getReceiver();
                    return null;
                })
                .collect(Collectors.toList());

        request.setAttribute("profile", profile);
        request.setAttribute("tracks", trackService.getProfileMusic(myId));
        request.setAttribute("friends", friends);
        ControllerUtils.forward(request, response, GetUrl.MY_PROFILE.getJspFileName());
    }
}
