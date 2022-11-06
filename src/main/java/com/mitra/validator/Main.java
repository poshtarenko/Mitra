package com.mitra.validator;

import com.mitra.db.dao.DaoFactory;
import com.mitra.dto.mapper.DtoMapperFactory;

public class Main {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        daoFactory.getProfileDao();
    }
}
