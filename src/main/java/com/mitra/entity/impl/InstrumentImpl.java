package com.mitra.entity.impl;

import com.mitra.entity.Instrument;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstrumentImpl implements Instrument {
    Integer id;
    String name;
}
