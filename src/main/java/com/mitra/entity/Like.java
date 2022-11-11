package com.mitra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Like implements Identifiable<Integer> {
    Integer id;
    Integer senderId;
    Integer receiverId;
    Reaction reaction;
}
