package com.java;

import javax.swing.*;
import java.util.ArrayList;

public class Passport {
    private ArrayList<JTextField> textFields;

    public Passport(ArrayList<JTextField> textFields) {

        this.textFields = textFields;
    }

    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }
}