package com.mitra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Like implements Identifiable<Integer> {
    Integer id;
    Profile sender;
    Profile receiver;
    Reaction reaction;
}
