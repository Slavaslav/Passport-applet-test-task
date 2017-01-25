package com.java;

import javax.swing.*;
import java.util.ArrayList;

public class Passport {
    public static final String[] PASSPORT_FILED_NAMES = new String[]{"passportNo", "surname", "given names", "patronymic",
            "date birth", "place birth", "authority", "date of issue"};
    private ArrayList<String> passportData = new ArrayList<>();

    public Passport(ArrayList<JTextField> textFields) {
        for (JTextField field : textFields) {
            passportData.add(field.getText());
        }
    }

    public ArrayList<String> getPassportData() {
        return passportData;
    }
}