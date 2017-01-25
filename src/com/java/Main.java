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
        JFrame frame = new JFrame("Input Passport Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Button("Test"), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}