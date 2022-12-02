package com.mitra.dto;

import com.mitra.entity.Message;
import com.mitra.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ChatDto implements Dto {
    Integer id;
    ProfileDto firstProfile;
    ProfileDto secondProfile;
    List<Message> messages;
}
