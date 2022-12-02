package com.mitra.entity.impl;

import com.mitra.entity.Chat;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageImpl implements Message {
    Long id;
    Profile sender;
    Chat chat;
    String message;
    LocalDateTime time;
    boolean isRead;
}
