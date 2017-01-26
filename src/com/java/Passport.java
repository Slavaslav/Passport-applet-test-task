package com.java;

import java.util.ArrayList;

public class Passport {
    public static final String[] PASSPORT_FILED_NAMES = new String[]{"passportNo", "surname", "given names", "patronymic",
            "date birth", "place birth", "authority", "date of issue"};
    private ArrayList<String> passportData;

    public Passport(ArrayList<String> passportData) {
        this.passportData = passportData;

    }

    public ArrayList<String> getPassportData() {
        return passportData;
    }
}