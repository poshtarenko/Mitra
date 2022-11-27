package com.mitra.entity.impl;

import com.mitra.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialityImpl implements Speciality {
    Integer id;
    String name;
}
