package com.java;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Applet {

    @Override
    public void init() {
        Button openWindowInputPassportData = new Button("Open a new window for entering the passport data");
        openWindowInputPassportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewJFrameInputPassportData();
            }
        });
        this.add(openWindowInputPassportData);
    }

    private void openNewJFrameInputPassportData() {
        final int columnsTextField = 30;
        TextField passportNoTextField = new TextField(columnsTextField);
        TextField surnameTextField = new TextField(columnsTextField);
        TextField givenNamesTextField = new TextField(columnsTextField);
        TextField patronymicTextField = new TextField(columnsTextField);
        TextField dateBirthTextField = new TextField(columnsTextField);
        TextField placeBirthTextField = new TextField(columnsTextField);
        TextField authorityTextField = new TextField(columnsTextField);
        TextField dateOfIssueTextField = new TextField(columnsTextField);
        Button okButton = new Button("Ok");
        Button closeButton = new Button("Close");

        JFrame frame = new JFrame("Input Passport Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanelRoot = new JPanel();
        jPanelRoot.setLayout(new BoxLayout(jPanelRoot, BoxLayout.Y_AXIS));

        JPanel jPanelTextFields = new JPanel();
        jPanelTextFields.setLayout(new BoxLayout(jPanelTextFields, BoxLayout.Y_AXIS));
        jPanelTextFields.add(passportNoTextField, BorderLayout.PAGE_START);
        jPanelTextFields.add(surnameTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(givenNamesTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(patronymicTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(dateBirthTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(placeBirthTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(authorityTextField, BorderLayout.AFTER_LAST_LINE);
        jPanelTextFields.add(dateOfIssueTextField, BorderLayout.AFTER_LAST_LINE);

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
        jPanelButtons.add(okButton);
        jPanelButtons.add(closeButton);

        jPanelRoot.add(jPanelTextFields);
        jPanelRoot.add(jPanelButtons);

        frame.add(jPanelRoot);
        frame.pack();
        frame.setVisible(true);
    }
}