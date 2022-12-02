package com.mitra.dto;

import com.mitra.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class MessageDto implements Dto {
    Long id;
    Profile sender;
    ChatDto chat;
    String message;
    LocalDateTime time;
    boolean isRead;
}