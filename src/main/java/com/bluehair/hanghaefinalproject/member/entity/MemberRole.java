package com.bluehair.hanghaefinalproject.member.entity;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN(Authority.ADMIN),
    SILVER(Authority.SILVER),
    GOLD(Authority.GOLD),
    PLATIUNUM(Authority.PLATIUNUM);
    private final String authority;
    MemberRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String SILVER = "ROLE_SILVER";
        public static final String GOLD = "ROLE_GOLD";
        public static final String PLATIUNUM = "ROLE_PLATINUM";
    }
}
