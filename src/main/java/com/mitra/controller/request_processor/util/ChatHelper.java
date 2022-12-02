package com.mitra.controller.request_processor.util;

import com.mitra.dto.ChatDto;
import com.mitra.dto.ProfileDto;

import java.util.Collections;

public final class ChatHelper {
    public static ChatDto putFriendIdToSecondPlace(ChatDto chat, int myId) {
        ProfileDto myProfile = null;
        ProfileDto friendProfile = null;

        if (chat.getFirstProfile().getId().equals(myId)) {
            myProfile = chat.getFirstProfile();
            friendProfile = chat.getSecondProfile();
        } else if (chat.getSecondProfile().getId().equals(myId)) {
            myProfile = chat.getSecondProfile();
            friendProfile = chat.getFirstProfile();
        }
        return new ChatDto(
                chat.getId(),
                myProfile,
                friendProfile,
                Collections.emptyList()
        );
    }
}
