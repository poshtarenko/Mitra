package com.mitra.db.filter;

import com.mitra.entity.Gender;
import com.mitra.entity.Instrument;
import com.mitra.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileFilter implements Filter {
    String name;
    Gender gender;
    Integer minAge;
    Integer maxAge;
    String city;
    String localArea;
    String region;
    List<Instrument> instruments;
    List<Speciality> specialities;
}
