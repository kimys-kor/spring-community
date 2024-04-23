package com.community.api.model.base;

public enum UserGrade {
    BRONZE("bronze"),
    SILVER("silver"),
    GOLD("gold"),
    PLATINUM("platinum")
    ;

    private final String code;

    UserGrade(String code) {
        this.code = code;
    }

    public static UserGrade of(String code) {
        if (code == null) {
            throw new IllegalStateException();
        }

        for (UserGrade ug : UserGrade.values()) {
            if (ug.code.equals(code)) {
                return ug;
            }
        }
        throw new IllegalStateException("일치하는 유저 등급이 없습니다.");
    }
}
