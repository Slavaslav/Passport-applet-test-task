package com.java;

import javax.swing.*;
import java.util.ArrayList;

public class Passport {
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