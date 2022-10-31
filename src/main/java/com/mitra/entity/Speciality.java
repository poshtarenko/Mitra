package com.mitra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Speciality implements Identifiable<Integer>{
    Integer id;
    String name;
}
