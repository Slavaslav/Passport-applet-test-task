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
        final JFrame frame = new JFrame("Input Passport Data");

        Label passportNoLabel = new Label("Input passportNo:");
        Label surnameLabel = new Label("Input surname:");
        Label givenNamesLabel = new Label("Input given names:");
        Label patronymicLabel = new Label("Input patronymic:");
        Label dateBirthLabel = new Label("Input date birth:");
        Label placeBirthLabel = new Label("Input place birth:");
        Label authorityLabel = new Label("Input authority:");
        Label dateOfIssueLabel = new Label("Input date of issue:");

        final int columnsTextField = 40;
        TextField passportNoTextField = new TextField(columnsTextField);
        TextField surnameTextField = new TextField(columnsTextField);
        TextField givenNamesTextField = new TextField(columnsTextField);
        TextField patronymicTextField = new TextField(columnsTextField);
        TextField dateBirthTextField = new TextField(columnsTextField);
        TextField placeBirthTextField = new TextField(columnsTextField);
        TextField authorityTextField = new TextField(columnsTextField);
        TextField dateOfIssueTextField = new TextField(columnsTextField);
        Button okButton = new Button("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click ok");
            }
        });
        Button closeButton = new Button("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel jPanelTextFields = new JPanel();
        jPanelTextFields.setLayout(new BoxLayout(jPanelTextFields, BoxLayout.Y_AXIS));
        jPanelTextFields.add(passportNoLabel);
        jPanelTextFields.add(passportNoTextField);
        jPanelTextFields.add(surnameLabel);
        jPanelTextFields.add(surnameTextField);
        jPanelTextFields.add(givenNamesLabel);
        jPanelTextFields.add(givenNamesTextField);
        jPanelTextFields.add(patronymicLabel);
        jPanelTextFields.add(patronymicTextField);
        jPanelTextFields.add(dateBirthLabel);
        jPanelTextFields.add(dateBirthTextField);
        jPanelTextFields.add(placeBirthLabel);
        jPanelTextFields.add(placeBirthTextField);
        jPanelTextFields.add(authorityLabel);
        jPanelTextFields.add(authorityTextField);
        jPanelTextFields.add(dateOfIssueLabel);
        jPanelTextFields.add(dateOfIssueTextField);

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
        jPanelButtons.add(okButton);
        jPanelButtons.add(closeButton);

        JPanel jPanelRoot = new JPanel();
        jPanelRoot.setLayout(new BoxLayout(jPanelRoot, BoxLayout.Y_AXIS));
        jPanelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelRoot.add(jPanelTextFields);
        jPanelRoot.add(jPanelButtons);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jPanelRoot);
        frame.pack();
        frame.setVisible(true);
    }
}