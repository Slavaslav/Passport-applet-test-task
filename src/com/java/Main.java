package com.java;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(new Label("Input passportNo:"));
        labels.add(new Label("Input surname:"));
        labels.add(new Label("Input given names:"));
        labels.add(new Label("Input patronymic:"));
        labels.add(new Label("Input date birth:"));
        labels.add(new Label("Input place birth:"));
        labels.add(new Label("Input authority:"));
        labels.add(new Label("Input date of issue:"));

        final int columnsTextField = 40;
        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));
        textFields.add(new TextField(columnsTextField));

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

        JPanel jPanelLabelAndTextFields = new JPanel();
        jPanelLabelAndTextFields.setLayout(new BoxLayout(jPanelLabelAndTextFields, BoxLayout.Y_AXIS));

        int biggerArraySize = labels.size() >= textFields.size() ? labels.size() : textFields.size();
        for (int i = 0; i < biggerArraySize; i++) {
            jPanelLabelAndTextFields.add(labels.get(i));
            jPanelLabelAndTextFields.add(textFields.get(i));
        }

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
        jPanelButtons.add(okButton);
        jPanelButtons.add(closeButton);

        JPanel jPanelRoot = new JPanel();
        jPanelRoot.setLayout(new BoxLayout(jPanelRoot, BoxLayout.Y_AXIS));
        jPanelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelRoot.add(jPanelLabelAndTextFields);
        jPanelRoot.add(jPanelButtons);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jPanelRoot);
        frame.pack();
        frame.setVisible(true);
    }
}