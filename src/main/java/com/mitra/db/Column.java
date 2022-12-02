package com.mitra.db;

public enum Column {
    ;

    public enum ROLE {
        ID,
        ROLE;

        @Override
        public String toString() {
            return Table.ROLE + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum USER {
        ID,
        EMAIL,
        PASSWORD,
        ROLE_ID;

        @Override
        public String toString() {
            return Table.USER + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum PROFILE {
        ID,
        NAME,
        AGE,
        GENDER_ID,
        TEXT,
        CITY_ID,
        PREVIEW_MUSIC_ID,
        PHOTO_PATH;

        @Override
        public String toString() {
            return Table.PROFILE + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum LIKE {
        SENDER_ID,
        RECEIVER_ID,
        REACTION;

        @Override
        public String toString() {
            return Table.LIKE + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum GENDER {
        ID,
        GENDER;

        @Override
        public String toString() {
            return Table.GENDER + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum MUSIC {
        ID,
        NAME,
        AUTHOR,
        FILE_PATH,
        PROFILE_ID;

        @Override
        public String toString() {
            return Table.MUSIC + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum INSTRUMENT {
        ID,
        NAME;

        @Override
        public String toString() {
            return Table.INSTRUMENT + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum PROFILE_INSTRUMENT {
        PROFILE_ID,
        INSTRUMENT_ID;

        @Override
        public String toString() {
            return Table.PROFILE_INSTRUMENT + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum SPECIALITY {
        ID,
        NAME;

        @Override
        public String toString() {
            return Table.SPECIALITY + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum PROFILE_SPECIALITY {
        PROFILE_ID,
        SPECIALITY_ID;

        @Override
        public String toString() {
            return Table.PROFILE_SPECIALITY + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum CITY {
        ID,
        NAME,
        LOCAL_AREA_ID;

        @Override
        public String toString() {
            return Table.CITY + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum LOCAL_AREA {
        ID,
        NAME,
        REGION_ID;

        @Override
        public String toString() {
            return Table.LOCAL_AREA + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum REGION {
        ID,
        NAME,
        COUNTRY_ID;

        @Override
        public String toString() {
            return Table.REGION + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum COUNTRY {
        ID,
        NAME;

        @Override
        public String toString() {
            return Table.COUNTRY + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum CHAT {
        ID,
        PROFILE1_ID,
        PROFILE2_ID;

        @Override
        public String toString() {
            return Table.CHAT + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

    public enum MESSAGE {
        ID,
        SENDER_ID,
        MESSAGE,
        TIME,
        IS_READ,
        CHAT_ID;

        @Override
        public String toString() {
            return Table.MESSAGE + "." + shortName();
        }

        public String shortName() {
            return name().toLowerCase();
        }
    }

}
