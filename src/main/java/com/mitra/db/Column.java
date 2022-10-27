package com.mitra.db;

public enum Column {
    ;

    public enum ROLE {
        id,
        role;

        @Override
        public String toString() {
            return Table.ROLE + "." + super.toString();
        }
    }

    public enum USER {

        id,
        email,
        password,
        role_id;


        @Override
        public String toString() {
            return Table.USER + "." + super.toString();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }
}
