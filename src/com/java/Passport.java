package com.java;

import java.util.List;

public class Passport {
    public static final String INPUT = "Input ";
    public static final String DOTS = ":";
    public static final String[] PASSPORT_FILED_NAMES = new String[]{"passportNo", "surname", "given names", "patronymic",
            "date birth", "place birth", "authority", "date of issue"};
    private List<String> passportData;

    public Passport(List<String> passportData) {
        this.passportData = passportData;

    }

    public List<String> getPassportData() {
        return passportData;
    }
}